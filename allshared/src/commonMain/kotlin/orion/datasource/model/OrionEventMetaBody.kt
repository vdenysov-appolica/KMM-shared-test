package com.holidayextras.orion.datasource.model

import com.holidayextras.orion.util.getLocalDateTimeNow
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OrionEventMetaBody(
    val event: OrionEventMetaEventBody,
    val log: List<OrionEventMetaLogBody>
)

@Serializable
internal data class OrionEventMetaLogBody(
    val system: String = "publisher",
    val key: String = "version",
    val value: String = "4.0.0",
    val timestamp: LocalDateTime = getLocalDateTimeNow()
)

/*
 * [type] - Actual name of the event
 * [isClient] - Always true
 * [environment] - Staging or Prod
 * [schemaVersion] - Version of the schema.
 * [published] - Date now
 */
@Serializable
internal data class OrionEventMetaEventBody(
    val type: String,
    val isClient: Boolean = true,
    val service: String = "hxmobileapp",
    val organisation: String = "Holiday Extras Limited",
    val environment: OrionEnvironment,
    val dataContext: OrionDataContext = OrionDataContext.COLLECTOR_STREAMING,
    val schemaVersion: String,
    val published: LocalDateTime = getLocalDateTimeNow()
)

@Serializable
internal enum class OrionDataContext {
    @SerialName("collector__streaming")
    COLLECTOR_STREAMING,

    @SerialName("payment__streaming")
    PAYMENT_STREAMING,

    @SerialName("fraud__streaming")
    FRAUD_STREAMING,

    @SerialName("finance__streaming")
    FINANCE_STREAMING
}