package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.data.local.model.UserDbModel
import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(): Flow<UserDbModel?>
    fun getTokens(): Flow<Tokens?>
    suspend fun saveToken(tokens: Tokens)
    suspend fun clearUserData()
}