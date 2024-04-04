package com.zerogravity.moonlight.mobile.common.domain.usecase

import com.zerogravity.moonlight.mobile.common.data.repository.ServiceRepository
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.DataResult
import com.zerogravity.moonlight.mobile.common.domain.WhileUiSubscribed
import com.zerogravity.moonlight.mobile.common.domain.asResult
import com.zerogravity.moonlight.mobile.common.domain.error.AppError
import com.zerogravity.moonlight.mobile.common.domain.error.toAppError
import com.zerogravity.moonlight.shared.domain.model.dto.Category
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class HomeFeedUiState(
    val isRefreshing: Boolean, val appError: AppError?, val categoriesState: CategoriesUiState
)

sealed class HomeFeedUiEvent {
    data object RefreshData : HomeFeedUiEvent()
    data object ErrorConsumed : HomeFeedUiEvent()
}

sealed class CategoriesUiState {
    data class Populated(val categories: List<Category>) : CategoriesUiState()
    data object Empty : CategoriesUiState()
}

const val HomeFeedUseCaseTag = "HomeFeedUseCase"

class HomeFeedUseCase(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : KoinComponent {

    private val serviceRepository: ServiceRepository by inject()
    private val appLogger: AppLogger by inject()

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        coroutineScope.launch {
            appLogger.d("$HomeFeedUseCaseTag: exceptionHandler : ${exception.message}")
            error.emit(exception.toAppError())
        }
    }

    private val categories: Flow<DataResult<List<Category>>> =
        serviceRepository.getCategories().asResult()
    private val isRefreshing = MutableStateFlow(false)

    private val error = MutableStateFlow<AppError?>(null)

    private var homeFeedUiState = HomeFeedUiState(
        isRefreshing = false, appError = null, CategoriesUiState.Empty
    )

    val uiState = combine(categories, isRefreshing, error) { categoryList, refreshing, error ->
        var appError: AppError? = null
        var loading = false
        var categoriesUiState = homeFeedUiState.categoriesState
        when (categoryList) {
            is DataResult.Success -> categoriesUiState = if (categoryList.data.isNotEmpty()) {
                CategoriesUiState.Populated(categoryList.data)
            } else {
                CategoriesUiState.Empty
            }

            is DataResult.Loading -> loading = true
            is DataResult.Error -> appError = categoryList.exception?.toAppError()
        }
        if (appError == null) {
            appError = error
        }
        homeFeedUiState = homeFeedUiState.copy(
            isRefreshing = if(refreshing) refreshing else loading, appError = appError, categoriesState = categoriesUiState
        ).apply {
            appLogger.d("$HomeFeedUseCaseTag: combine: HomeFeedUiState : $this")
        }
        homeFeedUiState
    }.stateIn(
        scope = coroutineScope, started = WhileUiSubscribed, initialValue = homeFeedUiState
    )

    fun onRefresh() {
        coroutineScope.launch(exceptionHandler) {
            with(serviceRepository) {
                isRefreshing.emit(true)
                try {
                    syncCategories()
                    syncServices()
                } catch (t: Throwable) {
                    appLogger.d("$HomeFeedUseCaseTag: onRefresh : error : ${t.message}")
                    error.emit(t.toAppError())
                } finally {
                    isRefreshing.emit(false)
                }
            }
        }
    }

    fun onErrorConsumed() {
        coroutineScope.launch {
            error.emit(null)
        }
    }
}