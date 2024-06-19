package com.zerogravity.moonlight.mobile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.LogcatWriter
import com.zerogravity.moonlight.mobile.android.AndroidDataStore
import com.zerogravity.moonlight.mobile.android.AndroidDatabaseDriverFactory
import com.zerogravity.moonlight.mobile.android.AndroidUserPreferenceDataStore
import com.zerogravity.moonlight.mobile.common.data.local.DatabaseDriverFactory
import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserDataStore
import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserPreferenceDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.Dispatchers
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

actual fun platformName(): String = "Android"

actual fun platformModule() = module {
    single<HttpClientEngine> {
        OkHttp.create {
            addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
        }
    }

    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(get())
    }

    single<LogWriter> { LogcatWriter() }

    single<UserDataStore> {
        val context: Context = get()
        AndroidDataStore(context.dataStore, Dispatchers.IO)
    }

    single<UserPreferenceDataStore> {
        val context: Context = get()
        AndroidUserPreferenceDataStore(context.userPreferenceStore, Dispatchers.IO)
    }
}
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data_store")
val Context.userPreferenceStore: DataStore<Preferences> by preferencesDataStore(name = "user_preference_store")
