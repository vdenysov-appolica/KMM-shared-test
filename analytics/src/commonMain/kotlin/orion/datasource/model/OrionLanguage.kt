package com.holidayextras.orion.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrionLanguage {
    @SerialName("en")
    EN,

    @SerialName("de")
    DE
}