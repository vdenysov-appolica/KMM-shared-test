package com.holidayextras.orion.util

import platform.Foundation.NSUUID

internal actual fun getOrionUUIDString() =
    NSUUID().UUIDString.lowercase().replace("-", "")