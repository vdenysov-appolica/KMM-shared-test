package com.holidayextras.orion.util

internal sealed class NetworkResult<out T> {

    internal data class Success<out T>(val data: T) : NetworkResult<T>()

    internal sealed class Error : NetworkResult<Nothing>() {
        class NetworkError(val messages: List<String>) : Error()
        data object GenericError : Error()
    }
}

internal fun <U> NetworkResult<U>.mapNetworkResult(): Boolean {
    return when (this) {
        is NetworkResult.Success -> true
        is NetworkResult.Error -> false
    }
}