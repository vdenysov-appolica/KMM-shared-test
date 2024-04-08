package com.holidayextras.orion

import com.holidayextras.orion.datasource.OrionDataSource
import com.holidayextras.orion.datasource.OrionDataSourceImpl
import com.holidayextras.orion.datasource.api.OrionApi
import com.holidayextras.orion.datasource.model.OrionEventBody
import com.holidayextras.orion.datasource.model.OrionEventPageType
import com.holidayextras.orion.datasource.model.OrionLanguage
import com.holidayextras.orion.datasource.model.OrionPageSystem
import com.holidayextras.orion.mapper.OrionEventMapper
import com.holidayextras.orion.model.ActionObject
import com.holidayextras.orion.model.Environment
import com.holidayextras.orion.model.OrionEvent
import com.holidayextras.orion.network.createJson
import com.holidayextras.orion.network.createOrionClient
import com.holidayextras.orion.storage.LocalStorage
import com.holidayextras.orion.storage.SecureStorage
import com.holidayextras.orion.storage.SecureStorageKey
import com.holidayextras.orion.util.BundleHelper
import com.holidayextras.orion.util.RequestHandler
import com.holidayextras.orion.util.Timer
import com.holidayextras.orion.util.getOrionUUIDString
import com.holidayextras.orion.util.mapNetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class OrionManager(
    private val secureStorage: SecureStorage,
    bundleHelper: BundleHelper
) {
    private val json: Json = createJson()
    private val localStorage: LocalStorage = LocalStorage()
    private lateinit var orionDataSource: OrionDataSource

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val eventBuffer = mutableListOf<OrionEventBody>()
    private val batchSize = 10
    private val timer = Timer()

    private val eventMapper: OrionEventMapper = OrionEventMapper(
        secureStorage = secureStorage,
        localStorage = localStorage,
        bundleHelper = bundleHelper
    )

    init {
        timer.schedule({ flushBuffer() }, 10000)
    }

    private fun setEnvironment(environment: Environment) {
        val env = with(eventMapper) { environment.toOrionEnvironment() }
        localStorage.setEnvironment(env)
    }

    private fun checkVisitorId() {
        if (secureStorage.getString(SecureStorageKey.VISITOR_ID).isNullOrBlank()) {
            secureStorage.setString(getOrionUUIDString(), SecureStorageKey.VISITOR_ID)
        }
    }

    private fun flushBuffer() {
        if (eventBuffer.isNotEmpty()) {
            sendRequest(eventBuffer.toList())
            eventBuffer.clear()
        }
    }

    private fun sendRequest(events: List<OrionEventBody>) {
        coroutineScope.launch {
            orionDataSource.logMultipleEvents(events)
        }
    }

    fun init(environment: Environment, language: String, userAgent: String) {
        setEnvironment(environment)
        localStorage.setCurrentLanguage(if (language == "en") OrionLanguage.EN else OrionLanguage.DE)
        checkVisitorId()
        orionDataSource = OrionDataSourceImpl(
            orionApi = OrionApi(
                httpClient = createOrionClient(json, localStorage)
            ),
            requestHandler = RequestHandler(json)
        )
        localStorage.setUserAgent(userAgent)
    }

    fun setUserId(userId: String?) {
        secureStorage.setString(userId ?: "", SecureStorageKey.USER_ID)
    }

    fun setPage(page: OrionEventPageType, system: OrionPageSystem) {
        if (localStorage.getPageType() != page && localStorage.getPageSystem() != system) {
            localStorage.setPage(page)
            localStorage.setPageSystem(system)
            localStorage.setPageId(getOrionUUIDString())

            logEvent(OrionEvent.Load())
        }
    }

    suspend fun submitFeedback(feedback: String, url: String?): Boolean {
        val feedbackUrlLogEvent = logSingleEvent(OrionEvent.Capture(actionObject = ActionObject(name = "feedback_modal_url", value = url)))
        val feedbackSendLogEvent =
            logSingleEvent(OrionEvent.Capture(actionObject = ActionObject(name = "feedback_modal_send", value = feedback)))
        return feedbackUrlLogEvent && feedbackSendLogEvent
    }

    private suspend fun logSingleEvent(event: OrionEvent): Boolean {
        val body = with(eventMapper) {
            when (event) {
                is OrionEvent.Error -> event.toOrionErrorEventBody()
                is OrionEvent.Load -> event.toOrionLoadEventBody()
                is OrionEvent.CustomerState -> event.toOrionCustomerStateEventBody()
                is OrionEvent.Focus -> event.toOrionFocusEventBody()
                is OrionEvent.Capture -> event.toOrionCaptureEventBody()
                is OrionEvent.AutoCapture -> event.toOrionAutoCaptureEventBody()
                is OrionEvent.Click -> event.toOrionClickEventBody()
            }
        }
        return coroutineScope { orionDataSource.logEvent(body).mapNetworkResult() }
    }

    fun logEvent(event: OrionEvent) {
        val body = with(eventMapper) {
            when (event) {
                is OrionEvent.Error -> event.toOrionErrorEventBody()
                is OrionEvent.Load -> event.toOrionLoadEventBody()
                is OrionEvent.CustomerState -> event.toOrionCustomerStateEventBody()
                is OrionEvent.Focus -> event.toOrionFocusEventBody()
                is OrionEvent.Capture -> event.toOrionCaptureEventBody()
                is OrionEvent.AutoCapture -> event.toOrionAutoCaptureEventBody()
                is OrionEvent.Click -> event.toOrionClickEventBody()
            }
        }

        eventBuffer.add(body)
        if (eventBuffer.size >= batchSize) {
            flushBuffer()
        }
    }

    fun getSessionId(): String {
        return localStorage.getSessionId()
    }

    fun getVisitorId(): String? {
        return secureStorage.getString(SecureStorageKey.VISITOR_ID)
    }

    fun setUserAgent(userAgent: String) {
        localStorage.setUserAgent(userAgent)
    }

    fun onDestroy() {
        timer.onDestroy()
        coroutineScope.cancel()
    }
}