package com.holidayextras.orion.util

expect class Timer() {
    fun schedule(action: () -> Unit, repeatTimeMillis: Long)
    fun onDestroy()
}