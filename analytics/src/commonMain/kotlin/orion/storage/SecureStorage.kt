package com.holidayextras.orion.storage

internal enum class SecureStorageKey(val value: String) {
    USER_ID("user_id"),
    VISITOR_ID("visitor_id"),
    SESSION_ID("session_id")
}

expect class SecureStorage {
    internal fun setString(value: String, key: SecureStorageKey)
    internal fun getString(key: SecureStorageKey): String?
    internal fun removeObject(key: SecureStorageKey)
}