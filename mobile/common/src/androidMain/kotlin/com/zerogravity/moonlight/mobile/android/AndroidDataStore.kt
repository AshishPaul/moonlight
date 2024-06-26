package com.zerogravity.moonlight.mobile.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserDataStore
import com.zerogravity.moonlight.mobile.common.domain.JwtTokenDecoder
import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AndroidDataStore(
    private val dataStore: DataStore<Preferences>,
    jwtTokenDecoder: JwtTokenDecoder,
    private val ioDispatcher: CoroutineDispatcher
) : UserDataStore(jwtTokenDecoder) {

    private val tokensKey = stringPreferencesKey("user_data_store")

    override suspend fun saveToken(tokens: Tokens) {
        withContext(ioDispatcher) {
            dataStore.edit { settings ->
                settings[tokensKey] = Json.encodeToString(tokens)
            }
        }
    }

    override fun getTokens(): Flow<Tokens?> = dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[tokensKey]
        }.map {
            try {
                Json.decodeFromString<Tokens>(it!!)
            } catch (e: Exception) {
                null
            }
        }.flowOn(ioDispatcher)

    override suspend fun clear() {
        withContext(ioDispatcher) {
            dataStore.edit {
                it.clear()
            }
        }
    }
}