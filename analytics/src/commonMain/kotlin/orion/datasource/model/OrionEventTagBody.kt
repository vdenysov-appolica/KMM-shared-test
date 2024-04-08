package com.holidayextras.orion.datasource.model

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = OrionEventTagBodyDataSerializer::class)
internal sealed interface OrionEventTagBody {
    val name: String

    @SerialName("string")
    val string: String?
}

@Serializable
data class OrionEventTagStringBody(
    override val name: String,
    override val string: String
) : OrionEventTagBody

@Serializable
data class OrionEventTagNumberBody(
    override val name: String,
    // This should always be null. It's used only for polymorphic serialization.
    override val string: String? = null,
    val number: Int
) : OrionEventTagBody

internal object OrionEventTagBodyDataSerializer : JsonContentPolymorphicSerializer<OrionEventTagBody>(OrionEventTagBody::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<OrionEventTagBody> {
        return when {
            element.jsonObject["string"]?.jsonPrimitive?.content.isNullOrBlank() -> OrionEventTagNumberBody.serializer()
            else -> OrionEventTagStringBody.serializer()
        }
    }
}
