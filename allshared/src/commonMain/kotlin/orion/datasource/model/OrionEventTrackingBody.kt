package com.holidayextras.orion.datasource.model

import kotlinx.serialization.Serializable

/*
*  [visitorId] - Id of the device.
*  [sessionId] - Id of the session. Every time per launching create a new one.
*  [pageId] - Id of the screen. This should be recreated every time when open the screen.
* [pageActionId] - Unique identifier of event. Every time new id.
* [pageType] - Type of the page we navigate.
* [pageSystem] - Type of the page system.
* [agentCode] - There is a different agent code when you open the application differently.
*/
@Serializable
internal data class OrionEventTrackingBody(
    val visitorId: String,
    val sessionId: String,
    val pageId: String,
    val pageActionId: String,
    val pageType: OrionEventPageType,
    val pageSystem: OrionPageSystem,
    val pageProductType: OrionEventProductType?,
    val agentCode: String,
    val browser: OrionEventBrowserBody,
    val lang: OrionLanguage,
    val tags: List<OrionEventTagBody>
)