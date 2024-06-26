package com.zerogravity.moonlight.mobile.common.data.local.datastore

import com.zerogravity.moonlight.mobile.common.data.local.model.UserDbModel
import com.zerogravity.moonlight.mobile.common.domain.JwtTokenDecoder
import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

abstract class UserDataStore(
    private val jwtTokenDecoder: JwtTokenDecoder,
) {

    abstract suspend fun saveToken(tokens: Tokens)

    abstract fun getTokens(): Flow<Tokens?>

    abstract suspend fun clear()

    @OptIn(ExperimentalCoroutinesApi::class)
    open fun getUser(): Flow<UserDbModel?> {
        return getTokens().mapLatest {
            getUserFromToken(it?.accessToken)
        }
    }

    private fun getUserFromToken(token: String?): UserDbModel? {
        return jwtTokenDecoder.getUserFromToken(token)
    }
}