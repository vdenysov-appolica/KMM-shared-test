package com.holidayextras.orion.datasource.model

import kotlinx.serialization.Serializable

@Serializable
internal data class OrionResource(
    val protocol: String,
    val domain: String,
    val path: String,
    val search: String,
    val hash: String,
    val params: List<OrionKeyValue>
)