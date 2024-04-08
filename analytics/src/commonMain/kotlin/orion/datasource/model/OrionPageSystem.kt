package com.holidayextras.orion.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrionPageSystem {
    @SerialName("account")
    ACCOUNT,

    @SerialName("landing")
    LANDING,

    @SerialName("post_booking")
    POST_BOOKING
}