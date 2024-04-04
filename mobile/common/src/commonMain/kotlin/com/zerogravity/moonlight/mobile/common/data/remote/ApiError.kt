package com.zerogravity.moonlight.mobile.common.data.remote

import io.ktor.http.HttpStatusCode

fun parseApiError(httpStatusCode: HttpStatusCode, errorMessage: String): ApiException {
    return when (httpStatusCode) {
        HttpStatusCode.Unauthorized -> {
            ApiException.UnAuthorizedException
        }
        else -> {
            ApiException.UnknownServerError
        }
    }
}

sealed class ApiException : Exception() {
    data object UnAuthorizedException : ApiException()
    data object UnknownServerError : ApiException()
}