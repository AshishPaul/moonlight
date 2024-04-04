package com.zerogravity.moonlight.mobile.common.di

import com.zerogravity.moonlight.mobile.common.data.remote.ApiRoutes
import com.zerogravity.moonlight.mobile.common.data.remote.api.AuthenticationApi
import com.zerogravity.moonlight.mobile.common.data.remote.api.KtorAuthenticationApi
import com.zerogravity.moonlight.mobile.common.data.remote.api.KtorServiceApi
import com.zerogravity.moonlight.mobile.common.data.remote.api.ServiceApi
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun networkModule() = module {
    single { createHttpClient(get(), get(), get(), ApiRoutes.BASE_URL) }

    single<ServiceApi> { KtorServiceApi(get()) }
    single<AuthenticationApi> { KtorAuthenticationApi(get()) }
}

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    appLogger: AppLogger,
    baseUrl: String
) =
    HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(json)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    appLogger.d(message)
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            url(baseUrl)
        }
    }