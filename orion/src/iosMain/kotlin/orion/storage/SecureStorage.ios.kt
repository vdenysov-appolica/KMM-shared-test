package com.holidayextras.orion.storage

import com.holidayextras.orion.util.getOrionUUIDString

actual class SecureStorage {
    internal actual fun setString(
        value: String,
        key: SecureStorageKey
    ) {

    }

    internal actual fun getString(key: SecureStorageKey): String? {
        return if (key == SecureStorageKey.USER_ID) {
            "062b0e37c68638469b0d0ad0c4621c3ab155de06be2793a6d8281f3575799c68"
        } else {
            getOrionUUIDString()
        }

    }

    internal actual fun removeObject(key: SecureStorageKey) {
    }
}