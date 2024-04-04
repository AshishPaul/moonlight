package com.zerogravity.moonlight.mobile.common.data.local.datastore

import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.flow.Flow

interface UserDataStore {

    suspend fun saveToken(tokens: Tokens)

    fun getTokens(): Flow<Tokens?>

    suspend fun clear()

}