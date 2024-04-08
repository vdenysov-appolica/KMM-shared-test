package com.holidayextras.orion.storage

import com.holidayextras.orion.datasource.model.OrionEnvironment
import com.holidayextras.orion.datasource.model.OrionEventPageType
import com.holidayextras.orion.datasource.model.OrionLanguage
import com.holidayextras.orion.datasource.model.OrionPageSystem

internal class LocalStorage {

    private var environment: OrionEnvironment = OrionEnvironment.STAGING
    private var language: OrionLanguage = OrionLanguage.EN
    private var userAgent: String = "unavailable"
    private var orionSession: OrionSession = OrionSession()
    private var pageType: OrionEventPageType? = null
    private var pageSystem: OrionPageSystem? = null
    private var pageUUID: String = ""

    fun setEnvironment(environment: OrionEnvironment) {
        this.environment = environment
    }

    fun getEnvironment(): OrionEnvironment = environment

    fun setCurrentLanguage(lang: OrionLanguage) {
        language = lang
    }

    fun getCurrentLanguage() = language

    fun setUserAgent(userAgent: String) {
        this.userAgent = userAgent
    }

    fun getUserAgent() = userAgent

    fun getSessionId(): String {
        if (orionSession.isExpired) {
            orionSession = OrionSession()
        }

        return orionSession.sessionId
    }

    fun setPageSystem(pageSystem: OrionPageSystem) {
        this.pageSystem = pageSystem
    }

    fun getPageSystem(): OrionPageSystem? = pageSystem

    fun setPage(pageType: OrionEventPageType) {
        this.pageType = pageType
    }

    fun getPageType(): OrionEventPageType? = pageType

    fun setPageId(id: String) {
        pageUUID = id
    }

    fun getPageId() = pageUUID
}