package com.holidayextras.orion.datasource.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable(with = OrionEventBodySerializer::class)
internal sealed class OrionEventBody {
    abstract val meta: OrionEventMetaBody
    abstract val tracking: OrionEventTrackingBody
    abstract val user: OrionEventUserBody

    @Serializable
    data class OrionErrorEventBody(
        override val meta: OrionEventMetaBody,
        override val tracking: OrionEventTrackingBody,
        override val user: OrionEventUserBody,
        @SerialName("error") val error: OrionErrorBody
    ) : OrionEventBody()

    @Serializable
    data class OrionLoadEventBody(
        override val meta: OrionEventMetaBody,
        override val tracking: OrionEventTrackingBody,
        override val user: OrionEventUserBody,
        @SerialName("resource") val resource: OrionResource,
        val referrer: Map<String, String>?
    ) : OrionEventBody()

    @Serializable
    data class OrionCustomerStateEventBody(
        override val meta: OrionEventMetaBody,
        override val tracking: OrionEventTrackingBody,
        override val user: OrionEventUserBody,
        @SerialName("customerState") val customerState: OrionCustomerState
    ) : OrionEventBody()

    @Serializable
    data class OrionActionObjectEventBody(
        override val meta: OrionEventMetaBody,
        override val tracking: OrionEventTrackingBody,
        override val user: OrionEventUserBody,
        @SerialName("actionObject") val actionObject: OrionActionObject
    ) : OrionEventBody()
}

internal object OrionEventBodySerializer : JsonContentPolymorphicSerializer<OrionEventBody>(OrionEventBody::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<OrionEventBody> {
        return when {
            !element.jsonObject["error"]?.jsonObject.isNullOrEmpty() -> OrionEventBody.OrionErrorEventBody.serializer()
            !element.jsonObject["resource"]?.jsonObject.isNullOrEmpty() -> OrionEventBody.OrionLoadEventBody.serializer()
            !element.jsonObject["customerState"]?.jsonObject.isNullOrEmpty() -> OrionEventBody.OrionCustomerStateEventBody.serializer()
            !element.jsonObject["actionObject"]?.jsonObject.isNullOrEmpty() -> OrionEventBody.OrionActionObjectEventBody.serializer()
            else -> OrionEventBody.OrionErrorEventBody.serializer()
        }
    }
}
