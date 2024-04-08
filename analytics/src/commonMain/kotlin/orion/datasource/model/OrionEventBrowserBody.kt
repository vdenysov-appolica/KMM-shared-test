package com.holidayextras.orion.datasource.model

import kotlinx.serialization.Serializable

@Serializable
internal data class OrionEventBrowserBody(
    val userAgent: String
)
