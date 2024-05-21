package com.holidayextras.orion.util

actual class BundleHelper {
    internal actual fun getAppId(): String {
       return "com.holidayextras.TripappStaging"
    }

    internal actual fun getBundleVersion(): String {
        return "8.3.0"
    }
}