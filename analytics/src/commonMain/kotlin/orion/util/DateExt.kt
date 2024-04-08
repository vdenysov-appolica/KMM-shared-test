package com.holidayextras.orion.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal fun getLocalDateTimeNow(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())