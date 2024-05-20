package com.holidayextras.orion.util

import platform.Foundation.NSTimer
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

actual class Timer actual constructor() {

    private var timer: NSTimer? = null

    actual fun schedule(action: () -> Unit, repeatTimeMillis: Long) {
        invalidate()

        timer = NSTimer.scheduledTimerWithTimeInterval(
            repeatTimeMillis.toDouble() / 1000,
            repeats = true,
            block = { _ ->
                dispatch_async(
                    queue = dispatch_get_main_queue(),
                    block = {
                        println("xxx executing timer task")
                        action()
                    }
                )
            }
        )
    }

    actual fun onDestroy() {
        invalidate()
    }

    private fun invalidate() {
        timer?.invalidate()
        timer = null
    }
}