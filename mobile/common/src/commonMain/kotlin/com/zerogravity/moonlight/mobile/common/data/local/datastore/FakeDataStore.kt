package com.zerogravity.moonlight.mobile.common.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserDataStore
import com.zerogravity.moonlight.mobile.common.data.local.model.UserDbModel
import com.zerogravity.moonlight.mobile.common.domain.JwtTokenDecoder
import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FakeDataStore(
    jwtTokenDecoder: JwtTokenDecoder,
) : UserDataStore(jwtTokenDecoder) {

    private val userFlow = MutableSharedFlow<UserDbModel?>()

    override suspend fun saveToken(tokens: Tokens) {
        userFlow.emit(
            UserDbModel(
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )
    }

    override fun getTokens(): Flow<Tokens?> = flow { emit(null) }

    override suspend fun clear() {
        userFlow.emit(null)
    }

    override fun getUser(): Flow<UserDbModel?> {
        return flowOf(UserDbModel(
            "",
            "",
            "",
            "",
            "",
            ""
        ))
    }

}