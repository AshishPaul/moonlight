package com.zerogravity.moonlight.mobile.common.data.remote.api

import com.zerogravity.moonlight.shared.domain.model.response.CategoryListResponse
import com.zerogravity.moonlight.shared.domain.model.response.ServiceListResponse

class FakeServiceApi : ServiceApi {
    override suspend fun getAllCategories(): CategoryListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllServices(): ServiceListResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getServicesByCategory(categoryId: String): ServiceListResponse {
        TODO("Not yet implemented")
    }
}