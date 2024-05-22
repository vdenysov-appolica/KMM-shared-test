package com.holidayextras.orion.util

internal sealed class NetworkResult<out T> {

    internal data class Success<out T>(val data: T) : NetworkResult<T>()

    internal sealed class Error(val messages: List<String>) : NetworkResult<Nothing>() {
        class NetworkError(messages: List<String>) : Error(messages)
        class GenericError(messages: List<String>) : Error(messages)
    }
}

internal fun <U> NetworkResult<U>.mapNetworkResult(): Boolean {
    return when (this) {
        is NetworkResult.Success -> true
        is NetworkResult.Error -> false
    }
}