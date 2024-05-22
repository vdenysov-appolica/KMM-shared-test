package com.holidayextras.orion.mapper

import com.holidayextras.orion.datasource.model.OrionActionObject
import com.holidayextras.orion.datasource.model.OrionCustomerState
import com.holidayextras.orion.datasource.model.OrionEnvironment
import com.holidayextras.orion.datasource.model.OrionErrorBody
import com.holidayextras.orion.datasource.model.OrionEventBody
import com.holidayextras.orion.datasource.model.OrionEventBrowserBody
import com.holidayextras.orion.datasource.model.OrionEventMetaBody
import com.holidayextras.orion.datasource.model.OrionEventMetaEventBody
import com.holidayextras.orion.datasource.model.OrionEventMetaLogBody
import com.holidayextras.orion.datasource.model.OrionEventPageType
import com.holidayextras.orion.datasource.model.OrionEventTagStringBody
import com.holidayextras.orion.datasource.model.OrionEventTrackingBody
import com.holidayextras.orion.datasource.model.OrionEventUserBody
import com.holidayextras.orion.datasource.model.OrionPageSystem
import com.holidayextras.orion.datasource.model.OrionResource
import com.holidayextras.orion.model.ActionObject
import com.holidayextras.orion.model.Environment
import com.holidayextras.orion.model.ErrorInfo
import com.holidayextras.orion.model.EventCustomerState
import com.holidayextras.orion.model.EventInfo
import com.holidayextras.orion.model.OrionEvent
import com.holidayextras.orion.storage.LocalStorage
import com.holidayextras.orion.storage.SecureStorage
import com.holidayextras.orion.storage.SecureStorageKey
import com.holidayextras.orion.util.BundleHelper
import com.holidayextras.orion.util.Constants
import com.holidayextras.orion.util.getLocalDateTimeNow
import com.holidayextras.orion.util.getOrionUUIDString

internal class OrionEventMapper(
    private val secureStorage: SecureStorage,
    private val localStorage: LocalStorage,
    private val bundleHelper: BundleHelper
) {
    internal fun OrionEvent.Capture.toOrionCaptureEventBody(): OrionEventBody.OrionActionObjectEventBody {
        return OrionEventBody.OrionActionObjectEventBody(
            meta = createEventMetaBody(eventInfo),
            tracking = createTrackingBody(),
            user = createEventUserBody(),
            actionObject = actionObject.toOrionActionObject()
        )
    }

    internal fun OrionEvent.Error.toOrionErrorEventBody(): OrionEventBody.OrionErrorEventBody {
        return OrionEventBody.OrionErrorEventBody(
            meta = createEventMetaBody(eventInfo),
            tracking = createTrackingBody(),
            user = createEventUserBody(),
            error = createErrorBody(errorInfo)
        )
    }

    internal fun OrionEvent.Load.toOrionLoadEventBody(): OrionEventBody.OrionLoadEventBody {
        return OrionEventBody.OrionLoadEventBody(
            meta = createEventMetaBody(eventInfo),
            tracking = createTrackingBody(),
            user = createEventUserBody(),
            resource = createOrionResource(),
            referrer = null
        )
    }

    internal fun OrionEvent.CustomerState.toOrionCustomerStateEventBody(): OrionEventBody.OrionCustomerStateEventBody {
        return OrionEventBody.OrionCustomerStateEventBody(
            meta = createEventMetaBody(eventInfo),
            tracking = createTrackingBody(),
            user = createEventUserBody(),
            customerState = customerState.toOrionCustomerState()
        )
    }

    internal fun OrionEvent.Focus.toOrionFocusEventBody(): OrionEventBody.OrionActionObjectEventBody {
        return OrionEventBody.OrionActionObjectEventBody(
            meta = createEventMetaBody(eventInfo),
            tracking = createTrackingBody(),
            user = createEventUserBody(),
            actionObject = actionObject.toOrionActionObject()
        )
    }

    internal fun OrionEvent.AutoCapture.toOrionAutoCaptureEventBody(): OrionEventBody.OrionActionObjectEventBody {
        return OrionEventBody.OrionActionObjectEventBody(
            meta = createEventMetaBody(eventInfo),
            tracking = createTrackingBody(),
            user = createEventUserBody(),
            actionObject = actionObject.toOrionActionObject()
        )
    }

    internal fun OrionEvent.Click.toOrionClickEventBody(): OrionEventBody.OrionActionObjectEventBody {
        return OrionEventBody.OrionActionObjectEventBody(
            meta = createEventMetaBody(eventInfo),
            tracking = createTrackingBody(),
            user = createEventUserBody(),
            actionObject = actionObject.toOrionActionObject()
        )
    }

    internal fun Environment.toOrionEnvironment(): OrionEnvironment {
        return when (this) {
            Environment.DEMO,
            Environment.TEST,
            Environment.STAGING -> OrionEnvironment.STAGING

            Environment.PRODUCTION -> OrionEnvironment.PRODUCTION
        }
    }

    private fun createOrionResource(): OrionResource {
        return OrionResource(
            protocol = "https",
            domain = "www.holidayextras.com",
            path = "",
            search = "",
            hash = "",
            params = emptyList()
        )
    }

    private fun createEventMetaBody(eventInfo: EventInfo): OrionEventMetaBody {
        val logBody = OrionEventMetaLogBody(timestamp = getLocalDateTimeNow())
        return OrionEventMetaBody(
            event = createEventMetaEventBody(eventInfo),
            log = listOf(logBody)
        )
    }

    private fun createEventMetaEventBody(eventInfo: EventInfo): OrionEventMetaEventBody {
        return OrionEventMetaEventBody(
            type = eventInfo.type,
            schemaVersion = eventInfo.schema,
            published = getLocalDateTimeNow(),
            environment = localStorage.getEnvironment()
        )
    }

    private fun createTrackingBody(): OrionEventTrackingBody {
        val visitorId = secureStorage.getString(SecureStorageKey.VISITOR_ID) ?: ""
        val mobileTag = OrionEventTagStringBody(name = "On_Mobile", string = "yes")
        // source_platform_name = bundle id
        val bundleId = bundleHelper.getAppId()
        val platformTag = OrionEventTagStringBody(name = "source_platform_name", string = bundleId)
        // source_platform_version = version of the app
        val bundleVersion = bundleHelper.getBundleVersion()
        val versionTag = OrionEventTagStringBody(name = "source_platform_version", string = bundleVersion)
        val lang = localStorage.getCurrentLanguage()

        val userAgent = localStorage.getUserAgent()
        val browser = OrionEventBrowserBody(userAgent = userAgent)
        val pageActionId = getOrionUUIDString()
        val sessionId = localStorage.getSessionId()
        val pageType = localStorage.getPageType() ?: OrionEventPageType.HOME
        val pageSystem = localStorage.getPageSystem() ?: OrionPageSystem.ACCOUNT
        val agentCode =
            if (secureStorage.getString(SecureStorageKey.USER_ID) == null) Constants.AgentCode.DEFAULT_UNAUTH
            else Constants.AgentCode.DEFAULT_AUTH
        val pageId = localStorage.getPageId()

        return OrionEventTrackingBody(
            visitorId = visitorId,
            sessionId = sessionId,
            pageId = pageId,
            pageActionId = pageActionId,
            pageType = pageType,
            pageSystem = pageSystem,
            pageProductType = null,
            agentCode = agentCode,
            browser = browser,
            lang = lang,
            tags = listOf(
                mobileTag,
                platformTag,
                versionTag
            )
        )
    }

    private fun createEventUserBody(): OrionEventUserBody {
        val userId = secureStorage.getString(SecureStorageKey.USER_ID)
        return OrionEventUserBody(
            userExtId = if (userId.isNullOrBlank()) null else userId,
            tags = emptyList()
        )
    }

    private fun createErrorBody(errorInfo: ErrorInfo): OrionErrorBody {
        return OrionErrorBody(
            message = errorInfo.message,
            exception = errorInfo.isException,
            supplier = null,
            code = errorInfo.code,
            info = null,
            previousErrors = emptyList()
        )
    }

    private fun EventCustomerState.toOrionCustomerState(): OrionCustomerState {
        return when (this) {
            EventCustomerState.UNRECOGNISED -> OrionCustomerState.UNRECOGNISED
            EventCustomerState.RECOGNISED_WITH_EMAIL -> OrionCustomerState.RECOGNISED_WITH_EMAIL
            EventCustomerState.RECOGNISED_WITH_ACCOUNT -> OrionCustomerState.RECOGNISED_WITH_ACCOUNT
            EventCustomerState.AUTHENTICATED -> OrionCustomerState.AUTHENTICATED
        }
    }

    private fun ActionObject.toOrionActionObject(): OrionActionObject {
        return OrionActionObject(
            id = id,
            name = name,
            type = type,
            text = text,
            location = location,
            href = href,
            value = value
        )
    }
}