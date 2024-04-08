package com.holidayextras.orion.datasource.model

import kotlinx.serialization.Serializable

@Serializable
internal data class OrionEventUserBody(
    val userExtId: String?,
    val tags: List<OrionEventTagBody>
)
