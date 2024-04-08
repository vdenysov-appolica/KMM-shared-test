package com.holidayextras.orion.model

data class ErrorInfo(
    val message: String,
    val code: String,
    val isException: Boolean
)