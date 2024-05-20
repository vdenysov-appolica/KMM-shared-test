package com.holidayextras.orion.util

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

private const val NETWORK_TIMEOUT = 30000L

internal class RequestHandler(private val json: Json) {
    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): NetworkResult<T> {
        return withContext(dispatcher) {
            try {
                NetworkResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is ClientRequestException -> {
                        val raw = throwable.response.bodyAsText()
                        parseError(raw)
                    }

                    else -> NetworkResult.Error.GenericError(
                        listOf(throwable.message ?: throwable.toString())
                    )
                }
            }
        }
    }

    private fun parseError(rawJSON: String): NetworkResult.Error {
        return try {
//            val errorResponse = json.decodeFromString<ErrorResponse>(rawJSON)
            /*errorResponse.errors.map { it.description }*/
            NetworkResult.Error.NetworkError(messages = listOf(rawJSON))
        } catch (e: Exception) {
            NetworkResult.Error.GenericError(messages = listOf(e.message ?: e.toString()))
        }
    }
}