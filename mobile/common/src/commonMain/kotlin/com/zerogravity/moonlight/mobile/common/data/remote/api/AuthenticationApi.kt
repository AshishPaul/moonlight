package com.zerogravity.moonlight.mobile.common.data.remote.api

import com.zerogravity.moonlight.shared.domain.model.request.LoginWithCredentialRequest
import com.zerogravity.moonlight.shared.domain.model.request.TokenRequest
import com.zerogravity.moonlight.shared.domain.model.request.UserRequest
import com.zerogravity.moonlight.shared.domain.model.response.CommonResponse
import com.zerogravity.moonlight.shared.domain.model.response.TokenResponse

interface AuthenticationApi {
    suspend fun signUp(userRequest: UserRequest): CommonResponse
    suspend fun loginWithCredentials(loginWithCredentialRequest: LoginWithCredentialRequest): TokenResponse
    suspend fun loginWithIdToken(tokenRequest: TokenRequest): TokenResponse
    suspend fun refreshToken(tokenRequest: TokenRequest): TokenResponse
    suspend fun signOut(): CommonResponse
}