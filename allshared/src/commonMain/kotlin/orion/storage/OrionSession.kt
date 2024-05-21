package com.holidayextras.orion.storage

import com.holidayextras.orion.util.getOrionUUIDString
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.seconds

internal class OrionSession {
    private val dateCreated = Clock.System.now()

    val sessionId = getOrionUUIDString()

    val isExpired: Boolean
        get() {
            val secondsInDay: Long = 86400
            val timeElapsed = Clock.System.now().epochSeconds.seconds - dateCreated.epochSeconds.seconds
            return timeElapsed.inWholeSeconds > secondsInDay
        }
}