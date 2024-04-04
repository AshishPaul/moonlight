package com.zerogravity.moonlight.mobile.android

import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserDataStore
import com.zerogravity.moonlight.mobile.common.data.remote.model.response.Tokens

class NativeDataStore : UserDataStore {

    //TODO implement Androidx datastore

    private var tokens: Tokens? = null

    override suspend fun saveToken(tokens: Tokens) {
        this.tokens = tokens
    }

    override fun getTokens(): Tokens? {
        return tokens
    }

    override suspend fun clear() {
        tokens = null
    }
}