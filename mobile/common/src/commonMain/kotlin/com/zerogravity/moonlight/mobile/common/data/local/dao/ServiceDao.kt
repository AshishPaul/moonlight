package com.zerogravity.moonlight.mobile.common.data.local.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.zerogravity.moonlight.mobile.common.data.local.db.MoonlightMobileDb
import com.zerogravity.moonlight.mobile.common.data.local.model.CategoryDbModel
import com.zerogravity.moonlight.mobile.common.data.local.model.ServiceDbModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class ServiceDao(
    private val appDatabase: MoonlightMobileDb,
    private val databaseDispatcher: CoroutineDispatcher
) {
    fun getAllCategories(): Flow<List<CategoryDbModel>> {
        return appDatabase.categoriesQueries.selectAll(::toCategoryModel).asFlow()
            .mapToList(databaseDispatcher)
    }

    fun getAllServices(): Flow<List<ServiceDbModel>> {
        return appDatabase.servicesQueries.selectAll(::toServiceModel).asFlow()
            .mapToList(databaseDispatcher)
    }

    fun getServices(categoryId: String): Flow<List<ServiceDbModel>> {
        return appDatabase.servicesQueries.selectAll(::toServiceModel).asFlow()
            .mapToList(databaseDispatcher)
    }

    fun deleteAllCategories() {
        appDatabase.categoriesQueries.deleteAll()
    }

    fun deleteAllServices() {
        appDatabase.servicesQueries.deleteAll()
    }

    fun saveCategories(categories: List<CategoryDbModel>) {
        appDatabase.categoriesQueries.apply {
            transaction {
                categories.forEach {
                    insertItem(
                        it.id,
                        it.title,
                        it.description
                    )
                }
            }
        }
    }

    fun saveServices(services: List<ServiceDbModel>) {
        appDatabase.servicesQueries.apply {
            transaction {
                services.forEach {
                    insertItem(
                        it.id,
                        it.categoryId,
                        it.title,
                        it.description,
                        it.price
                    )
                }
            }
        }
    }

    private fun toCategoryModel(categoryId: String, title: String, description: String?) =
        CategoryDbModel(categoryId, title, description ?: "")

    private fun toServiceModel(
        serviceId: String,
        categoryId: String,
        title: String,
        description: String?,
        price: Double
    ) =
        ServiceDbModel(serviceId, categoryId, title, description ?: "", price)
}