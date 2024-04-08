package com.holidayextras.orion.model

data class EventInfo(
    val type: String,
    val schema: String
) {
    companion object {
        fun customerState(): EventInfo = EventInfo(
            type = "customer_state",
            schema = "v1.0.8"
        )

        fun capture(): EventInfo = EventInfo(
            type = "capture",
            schema = "v4.3.9"
        )

        fun autoCapture(): EventInfo = EventInfo(
            type = "auto_capture",
            schema = "v4.4.9"
        )

        fun click(): EventInfo = EventInfo(
            type = "click",
            schema = "v4.5.9"
        )

        fun focus(): EventInfo = EventInfo(
            type = "focus",
            schema = "v4.2.9"
        )

        fun load(): EventInfo = EventInfo(
            type = "load",
            schema = "v4.0.9"
        )

        fun error(): EventInfo = EventInfo(
            type = "error",
            schema = "v2.2.9"
        )
    }
}