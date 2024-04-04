package com.zerogravity.moonlight.mobile.common.data.remote.api

import com.zerogravity.moonlight.shared.domain.model.response.CategoryListResponse
import com.zerogravity.moonlight.shared.domain.model.response.ServiceListResponse

interface ServiceApi {
    suspend fun getAllCategories(): CategoryListResponse
    suspend fun getAllServices(): ServiceListResponse
    suspend fun getServicesByCategory(categoryId: String): ServiceListResponse
}