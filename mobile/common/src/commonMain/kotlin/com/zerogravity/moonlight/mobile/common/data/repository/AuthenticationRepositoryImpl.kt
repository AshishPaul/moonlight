package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.data.remote.api.AuthenticationApi
import com.zerogravity.moonlight.mobile.common.domain.model.AuthData
import com.zerogravity.moonlight.shared.domain.model.request.LoginWithCredentialRequest
import com.zerogravity.moonlight.shared.domain.model.request.TokenRequest
import com.zerogravity.moonlight.shared.domain.model.request.UserRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
    private val userRepository: UserRepository,
    private val authenticationApi: AuthenticationApi,
    private val ioDispatcher: CoroutineDispatcher
) : AuthenticationRepository {

    override fun isAuthenticated(): Flow<Boolean> =
        userRepository.getUser().map { it != null }

    override suspend fun signUp(userRequest: UserRequest) = withContext(ioDispatcher) {
        authenticationApi.signUp(userRequest)
    }

    override suspend fun authenticateWithEmailPassword(email: String, password: String) {
        withContext(ioDispatcher) {
            authenticationApi.loginWithCredentials(LoginWithCredentialRequest(email, password))
                .let {
                    userRepository.saveToken(it.tokens)
                }
        }
    }

    override suspend fun authenticateWithAuthProvider(authData: AuthData) {
        withContext(ioDispatcher) {
            authenticationApi.loginWithIdToken(TokenRequest(authData.idToken)).let {
                userRepository.saveToken(it.tokens)
            }
        }
    }

    override suspend fun logout() = withContext(ioDispatcher) {
        authenticationApi.signOut()
        userRepository.clearUserData()
    }
}