package com.zerogravity.moonlight.mobile

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.OSLogWriter
import com.zerogravity.moonlight.mobile.common.data.local.DatabaseDriverFactory
import com.zerogravity.moonlight.mobile.native.NativeDatabaseDriverFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual fun platformName(): String = "iOS"

actual fun platformModule() = module {
    single<HttpClientEngine> {
        Darwin.create() {
            configureRequest {
                setAllowsCellularAccess(true)
            }
        }
    }

    single<DatabaseDriverFactory> {
        NativeDatabaseDriverFactory()
    }

    single<LogWriter> { OSLogWriter() }

    single<UserDataStore> { NativeDataStore() }

    single<UserPreferenceDataStore> { NativeUserPreferenceDataStore() }


}