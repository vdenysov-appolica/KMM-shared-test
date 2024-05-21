package com.holidayextras.orion.util

actual class Timer actual constructor() {
    actual fun schedule(action: () -> Unit, repeatTimeMillis: Long) {
    }

    actual fun onDestroy() {
    }
}