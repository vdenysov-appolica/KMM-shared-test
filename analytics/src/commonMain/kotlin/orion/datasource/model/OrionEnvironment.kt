package com.holidayextras.orion.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class OrionEnvironment {
    @SerialName("production")
    PRODUCTION,

    @SerialName("staging")
    STAGING,

    @SerialName("test")
    TEST,

    @SerialName("development")
    DEVELOPMENT
}