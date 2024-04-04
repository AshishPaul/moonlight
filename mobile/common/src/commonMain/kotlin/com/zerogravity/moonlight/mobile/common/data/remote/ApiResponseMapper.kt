package com.zerogravity.moonlight.mobile.common.data.remote

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T : Any> HttpResponse.returnOkResponseOrThrow(): T {
    if (HttpStatusCode.OK == this.status) {
        return this.body()
    } else {
        throw parseApiError(this.status, this.body<String>().toString())
    }
}