package com.zerogravity.moonlight.mobile.common.data.remote.api

import com.zerogravity.moonlight.mobile.common.data.remote.ApiRoutes
import com.zerogravity.moonlight.mobile.common.data.remote.returnOkResponseOrThrow
import com.zerogravity.moonlight.shared.domain.model.request.LoginWithCredentialRequest
import com.zerogravity.moonlight.shared.domain.model.request.TokenRequest
import com.zerogravity.moonlight.shared.domain.model.request.UserRequest
import com.zerogravity.moonlight.shared.domain.model.response.CommonResponse
import com.zerogravity.moonlight.shared.domain.model.response.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorAuthenticationApi(
    private val httpClient: HttpClient
) : AuthenticationApi {
    override suspend fun signUp(userRequest: UserRequest): CommonResponse {
        return httpClient.post(ApiRoutes.Endpoint.SignUp.path) {
            contentType(ContentType.Application.Json)
            setBody(userRequest)
        }.returnOkResponseOrThrow()
    }


    override suspend fun loginWithCredentials(loginWithCredentialRequest: LoginWithCredentialRequest): TokenResponse {
        return httpClient.post(ApiRoutes.Endpoint.AuthWithCredential.path) {
            contentType(ContentType.Application.Json)
            setBody(loginWithCredentialRequest)
        }.returnOkResponseOrThrow()
    }

    override suspend fun loginWithIdToken(tokenRequest: TokenRequest): TokenResponse {
        return httpClient.post(ApiRoutes.Endpoint.AuthWithToken.path) {
            contentType(ContentType.Application.Json)
            setBody(tokenRequest)
        }.returnOkResponseOrThrow()
    }

    override suspend fun refreshToken(tokenRequest: TokenRequest): TokenResponse {
        return httpClient.post(ApiRoutes.Endpoint.RefreshToken.path) {
            contentType(ContentType.Application.Json)
            setBody(tokenRequest)
        }.returnOkResponseOrThrow()
    }

    override suspend fun signOut(): CommonResponse {
        return CommonResponse(true, "Success")
    }
}