package com.holidayextras.orion.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class OrionEventPageType {
    @SerialName("account")
    ACCOUNT,

    @SerialName("home")
    HOME,

    @SerialName("view_bookings")
    VIEW_BOOKINGS,

    @SerialName("booking_details")
    BOOKING_DETAILS,

    @SerialName("support")
    SUPPORT,

    @SerialName("your_trips")
    YOUR_TRIPS
}
