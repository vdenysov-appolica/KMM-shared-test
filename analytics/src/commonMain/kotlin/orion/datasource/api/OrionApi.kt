package com.holidayextras.orion.datasource.api

import com.holidayextras.orion.datasource.model.OrionEventBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class OrionApi(
    private val httpClient: HttpClient
) {
    suspend fun logEvent(body: OrionEventBody) {
        httpClient.post {
            setBody(body)
        }.body<Unit>()
    }

    suspend fun logMultipleEvents(bodyEvents: List<OrionEventBody>) {
        bodyEvents.forEach { orionBody ->
            httpClient.post {
                setBody(orionBody)
            }.body<Unit>()
        }
    }
}