package com.zerogravity.moonlight.mobile.common.domain.model

import com.zerogravity.moonlight.mobile.common.domain.error.AppError

enum class AuthProvider {
    GOOGLE,
    APPLE,
    FACEBOOK,
}

sealed class AuthResult {
    data class Successful(val authData: AuthData) : AuthResult()
    data class Error(val authError: AuthError) : AuthResult()
}

data class AuthData(val idToken: String)

sealed class AuthError {
    data class AccountNotFound(val error: Throwable) : AuthError()
    data class InvalidAuthData(val error: Throwable) : AuthError()
}

fun AuthError.toAppError(): AppError {
    return AppError.FailedToLogin
}

