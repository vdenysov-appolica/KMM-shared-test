package com.holidayextras.orion.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal enum class OrionCustomerState {
    @SerialName("unrecognised")
    UNRECOGNISED,

    @SerialName("recognisedWithEmail")
    RECOGNISED_WITH_EMAIL,

    @SerialName("recognisedWithAccount")
    RECOGNISED_WITH_ACCOUNT,

    @SerialName("authenticated")
    AUTHENTICATED
}
