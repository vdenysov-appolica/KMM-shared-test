package com.holidayextras.orion.storage

actual class SecureStorage {
    internal actual fun setString(
        value: String,
        key: SecureStorageKey
    ) {
    }

    internal actual fun getString(key: SecureStorageKey): String? {
        TODO("Not yet implemented")
    }

    internal actual fun removeObject(key: SecureStorageKey) {
    }
}