package com.zerogravity.moonlight.mobile.common.data.remote.api

import com.zerogravity.moonlight.mobile.common.data.remote.ApiRoutes
import com.zerogravity.moonlight.mobile.common.data.remote.returnOkResponseOrThrow
import com.zerogravity.moonlight.shared.domain.model.response.CategoryListResponse
import com.zerogravity.moonlight.shared.domain.model.response.ServiceListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class KtorServiceApi(
    private val httpClient: HttpClient,
) : ServiceApi {

    override suspend fun getAllCategories(): CategoryListResponse =
        httpClient.get(ApiRoutes.Endpoint.Category.path).returnOkResponseOrThrow()


    override suspend fun getAllServices(): ServiceListResponse =
        httpClient.get(ApiRoutes.Endpoint.Service.path).returnOkResponseOrThrow()


    override suspend fun getServicesByCategory(categoryId: String): ServiceListResponse =
        httpClient.get(
            ApiRoutes.Endpoint.ServiceByCategoryId.path.replace(
                "{categoryId}", categoryId
            )
        ).returnOkResponseOrThrow()
}