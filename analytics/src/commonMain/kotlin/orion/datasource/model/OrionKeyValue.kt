package com.holidayextras.orion.datasource.model

import kotlinx.serialization.Serializable

@Serializable
internal data class OrionKeyValue(
    val key: String,
    val value: String
)