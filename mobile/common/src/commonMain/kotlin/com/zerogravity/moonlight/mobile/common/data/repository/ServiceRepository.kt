package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.shared.domain.model.dto.Category
import com.zerogravity.moonlight.shared.domain.model.dto.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun getCategories(): Flow<List<Category>>
    fun getServicesByCategory(categoryId: String): Flow<List<Service>>

    @Throws(Throwable::class)
    suspend fun syncCategories(): List<Category>

    @Throws(Throwable::class)
    suspend fun syncServices(): List<Service>
}