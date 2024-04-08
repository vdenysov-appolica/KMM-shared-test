package com.holidayextras.orion.datasource

import com.holidayextras.orion.datasource.model.OrionEventBody
import com.holidayextras.orion.util.NetworkResult

internal interface OrionDataSource {

    suspend fun logEvent(body: OrionEventBody): NetworkResult<Unit>
    suspend fun logMultipleEvents(bodyEvents: List<OrionEventBody>): NetworkResult<Unit>
}