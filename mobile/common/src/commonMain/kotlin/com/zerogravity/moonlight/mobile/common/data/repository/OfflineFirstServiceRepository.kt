package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.data.local.dao.ServiceDao
import com.zerogravity.moonlight.mobile.common.data.local.model.CategoryDbModel
import com.zerogravity.moonlight.mobile.common.data.local.model.ServiceDbModel
import com.zerogravity.moonlight.mobile.common.data.remote.api.ServiceApi
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.mapper.categoryDbModel
import com.zerogravity.moonlight.mobile.common.domain.mapper.dto
import com.zerogravity.moonlight.mobile.common.domain.mapper.serviceDbModel
import com.zerogravity.moonlight.shared.domain.model.dto.Category
import com.zerogravity.moonlight.shared.domain.model.dto.Service
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class OfflineFirstServiceRepository(
    private val serviceApi: ServiceApi,
    private val serviceDao: ServiceDao,
    private val appLogger: AppLogger,
    private val ioDispatcher: CoroutineDispatcher
) : ServiceRepository {

    override fun getServicesByCategory(categoryId: String): Flow<List<Service>> {
        return serviceDao.getServices(categoryId)
            .map { it.map(ServiceDbModel::dto) }
            .flowOn(ioDispatcher).onEach {
                appLogger.d("OfflineFirstServiceRepository: getServicesByCategory: result: $it")
                if (it.isEmpty()) {
                    syncServices()
                }
            }
    }

    override fun getCategories(): Flow<List<Category>> {
        return serviceDao.getAllCategories()
            .map {
                it.map(CategoryDbModel::dto)
            }.flowOn(ioDispatcher)
            .onEach {
                appLogger.d("OfflineFirstServiceRepository: getCategories: result: $it")
                if (it.isEmpty()) {
                    syncCategories()
                }
            }
    }

    @Throws(Throwable::class)
    override suspend fun syncServices() = withContext(ioDispatcher) {
        serviceApi.getAllServices().items.also {
            if (it.isNotEmpty()) {
                appLogger.d("OfflineFirstServiceRepository: syncServices : result : $it")
                serviceDao.deleteAllServices()
                serviceDao.saveServices(it.map(Service::serviceDbModel))
            }
        }
    }

    @Throws(Throwable::class)
    override suspend fun syncCategories(): List<Category> = withContext(ioDispatcher) {
        serviceApi.getAllCategories().items.also {
            if (it.isNotEmpty()) {
                appLogger.d("OfflineFirstServiceRepository: syncCategories : result : $it")
                serviceDao.deleteAllCategories()
                serviceDao.saveCategories(it.map(Category::categoryDbModel))
            }
        }
    }
}