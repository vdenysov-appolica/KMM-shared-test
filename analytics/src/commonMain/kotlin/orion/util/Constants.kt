package com.holidayextras.orion.util

internal object Constants {

    internal object Network {
        const val ORION_URL_HOST = "orionUrlHost"
        const val TRAVELLER_KEY_HEADER = "X-Apikey"
        const val TRAVELLER_KEY = "IpAJQ8y5phlJh7YQikufql9ZHd5lWKx8UsYVGeK2y6IYGyAr"
        const val AUTHORIZATION = "Authorization"

        const val BASE_HOST_STAGING = "https://orion-staging.dock-yard.io/collect/"
        const val BASE_HOST_PROD = "https://collector.holidayextras.co.uk/collect/"
    }

    internal object AgentCode {
        const val DEFAULT_AUTH = "MBDS1"
        const val DEFAULT_UNAUTH = "MBAP1"
    }

    internal object Errors {
        const val PAGE_SYSTEM = "You need a page system to log event"
        const val PAGE_TYPE = "You need a page type to log event"
    }
}