package com.holidayextras.orion.datasource

import com.holidayextras.orion.datasource.api.OrionApi
import com.holidayextras.orion.datasource.model.OrionEventBody
import com.holidayextras.orion.util.NetworkResult
import com.holidayextras.orion.util.RequestHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class OrionDataSourceImpl(
    private val orionApi: OrionApi,
    private val requestHandler: RequestHandler
) : OrionDataSource {

    override suspend fun logEvent(body: OrionEventBody): NetworkResult<Unit> {
        return requestHandler.safeApiCall(Dispatchers.IO) {
            orionApi.logEvent(body)
        }
    }

    override suspend fun logMultipleEvents(bodyEvents: List<OrionEventBody>): NetworkResult<Unit> {
        return requestHandler.safeApiCall(Dispatchers.IO) {
            orionApi.logMultipleEvents(bodyEvents)
        }
    }
}