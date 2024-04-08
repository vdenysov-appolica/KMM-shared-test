package com.holidayextras.orion.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OrionErrorBody(
    val message: String,
    val source: OrionErrorEventSource = OrionErrorEventSource.CLIENT,
    val exception: Boolean?,
    val supplier: String?,
    val code: String,
    val info: String?,
    val previousErrors: List<OrionKeyValue>
)

@Serializable
internal enum class OrionErrorEventSource {
    @SerialName("client")
    CLIENT,

    @SerialName("server")
    SERVER
}