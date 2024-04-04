package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.domain.model.AuthData
import com.zerogravity.moonlight.shared.domain.model.request.UserRequest
import com.zerogravity.moonlight.shared.domain.model.response.CommonResponse
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun isAuthenticated(): Flow<Boolean>

    suspend fun signUp(userRequest: UserRequest): CommonResponse

    suspend fun authenticateWithEmailPassword(email: String, password: String)

    suspend fun authenticateWithAuthProvider(authData: AuthData)
    suspend fun logout()

}