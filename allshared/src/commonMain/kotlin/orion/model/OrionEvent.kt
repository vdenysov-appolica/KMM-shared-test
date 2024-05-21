package com.holidayextras.orion.model

sealed class OrionEvent {
    abstract val eventInfo: EventInfo

    data class Error(
        override val eventInfo: EventInfo = EventInfo.error(),
        val errorInfo: ErrorInfo
    ) : OrionEvent()

    data class Load(override val eventInfo: EventInfo = EventInfo.load()) : OrionEvent()
    data class CustomerState(
        override val eventInfo: EventInfo = EventInfo.customerState(),
        val customerState: EventCustomerState
    ) : OrionEvent()

    data class Focus(
        override val eventInfo: EventInfo = EventInfo.focus(),
        val actionObject: ActionObject
    ) : OrionEvent()

    data class Click(
        override val eventInfo: EventInfo = EventInfo.click(),
        val actionObject: ActionObject
    ) : OrionEvent()

    data class Capture(
        override val eventInfo: EventInfo = EventInfo.capture(),
        val actionObject: ActionObject
    ) : OrionEvent()

    data class AutoCapture(
        override val eventInfo: EventInfo = EventInfo.autoCapture(),
        val actionObject: ActionObject
    ) : OrionEvent()
}