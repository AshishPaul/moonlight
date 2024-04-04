package com.zerogravity.moonlight.mobile.common.domain.error

import io.ktor.client.network.sockets.ConnectTimeoutException

fun Throwable.toAppError(): AppError {
    return when(this) {
        is ConnectTimeoutException -> {
            AppError.NetworkError
        }
        else -> {
            AppError.UnknownError
        }
    }
}