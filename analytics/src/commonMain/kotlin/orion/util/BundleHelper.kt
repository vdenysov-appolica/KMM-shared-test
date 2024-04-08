package com.holidayextras.orion.util

expect class BundleHelper {
    internal fun getAppId(): String
    internal fun getBundleVersion(): String
}