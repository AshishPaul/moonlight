package com.zerogravity.moonlight.mobile.common.domain.error

enum class AppError(val errorMessage: String) {
    UnknownError("Something went wrong. Please try again"),
    NetworkError("Please check your internet connection"),
    FailedToLogin("Failed to login. Please try again"),
}