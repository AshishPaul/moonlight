@file:OptIn(ExperimentalCoroutinesApi::class)

package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserDataStore
import com.zerogravity.moonlight.mobile.common.data.local.model.UserDbModel
import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override fun getUser(): Flow<UserDbModel?> {
        return userDataStore.getUser()
    }

    override fun getTokens(): Flow<Tokens?> {
        return userDataStore.getTokens()
    }

    override suspend fun saveToken(tokens: Tokens) {
        withContext(ioDispatcher) {
            userDataStore.saveToken(tokens)
        }
    }

    override suspend fun clearUserData() {
        userDataStore.clear()
    }
}