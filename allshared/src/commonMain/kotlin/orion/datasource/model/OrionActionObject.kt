package com.holidayextras.orion.datasource.model

import kotlinx.serialization.Serializable

@Serializable
internal data class OrionActionObject(
    val id: String?,
    val name: String,
    val type: String?,
    val text: String?,
    val location: String?,
    val href: String?,
    val value: String?
)
