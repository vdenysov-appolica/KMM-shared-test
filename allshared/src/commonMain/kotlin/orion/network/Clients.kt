package com.holidayextras.orion.network

import co.touchlab.kermit.Logger
import com.holidayextras.orion.datasource.model.OrionEnvironment
import com.holidayextras.orion.storage.LocalStorage
import com.holidayextras.orion.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

@OptIn(ExperimentalSerializationApi::class)
internal fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
    namingStrategy = JsonNamingStrategy.SnakeCase
}

internal fun createOrionClient(
    json: Json,
    localStorage: LocalStorage
) = HttpClient {
    expectSuccess = true

    defaultRequest {
//        url {
//            protocol = URLProtocol.HTTPS
//            host = if (localStorage.getEnvironment() == OrionEnvironment.STAGING) Constants.Network.BASE_HOST_STAGING
//            else Constants.Network.BASE_HOST_PROD
//        }

        // use when instead of if
        val host = if (localStorage.getEnvironment() == OrionEnvironment.STAGING) {
            Constants.Network.BASE_HOST_STAGING
        } else {
            Constants.Network.BASE_HOST_PROD
        }

        Logger.d { "Base url:${this.host}" }
//        headers.append("Content-Type", "application/json")
        url(host)
        contentType(ContentType.Application.Json)
        headers.append(Constants.Network.TRAVELLER_KEY_HEADER, Constants.Network.TRAVELLER_KEY)
    }

    install(ContentNegotiation) {
        json(
            json = json
        )
    }

    install(ResponseObserver) {
        onResponse { response ->
            Logger.d { "HTTP status: ${response.status.value}" }
        }
    }

    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                Logger.d { message }
            }
        }
        level = LogLevel.BODY
    }

    install(HttpTimeout)
}