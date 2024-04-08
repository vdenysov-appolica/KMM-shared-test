package com.holidayextras.orion.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class OrionEventProductType {
    @SerialName("hotel")
    HOTEL,

    @SerialName("spa")
    SPA
}
