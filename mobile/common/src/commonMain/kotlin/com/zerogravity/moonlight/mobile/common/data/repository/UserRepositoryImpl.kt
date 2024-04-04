@file:OptIn(ExperimentalCoroutinesApi::class)

package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserDataStore
import com.zerogravity.moonlight.mobile.common.data.local.model.UserDbModel
import com.zerogravity.moonlight.mobile.common.domain.JwtTokenDecoder
import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val jwtTokenDecoder: JwtTokenDecoder,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    private val tokenFlow = userDataStore.getTokens()

    private val userFlow: Flow<UserDbModel?> = tokenFlow.mapLatest {
        getUserFromToken(it?.accessToken)
    }

    override fun getUser(): Flow<UserDbModel?> {
        return userFlow
    }

    override fun getTokens(): Flow<Tokens?> {
        return tokenFlow
    }

    override suspend fun saveToken(tokens: Tokens) {
        withContext(ioDispatcher) {
            userDataStore.saveToken(tokens)
        }
    }

    override suspend fun clearUserData() {
        userDataStore.clear()
    }

    private fun getUserFromToken(token: String?): UserDbModel? {
        return jwtTokenDecoder.getUserFromToken(token)
    }
}