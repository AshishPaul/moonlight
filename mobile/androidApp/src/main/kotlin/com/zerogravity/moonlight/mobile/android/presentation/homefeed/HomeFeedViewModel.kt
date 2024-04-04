package com.zerogravity.moonlight.mobile.android.presentation.homefeed

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.usecase.HomeFeedUiEvent
import com.zerogravity.moonlight.mobile.common.domain.usecase.HomeFeedUseCase

const val HomeFeedViewModelTag = "HomeFeedViewModel"

class HomeFeedViewModel(
    private val appLogger: AppLogger,
    val handle: SavedStateHandle
) : ViewModel() {

    private val homeFeedUseCase by lazy {
        HomeFeedUseCase(viewModelScope)
    }

    val homeFeedUiState = homeFeedUseCase.uiState

    fun onHomeFeedUiEvent(homeFeedUiEvent: HomeFeedUiEvent) {
        appLogger.d("$HomeFeedViewModelTag: onHomeFeedUiEvent: $homeFeedUiEvent")
        when (homeFeedUiEvent) {
            HomeFeedUiEvent.ErrorConsumed -> homeFeedUseCase.onErrorConsumed()
            HomeFeedUiEvent.RefreshData -> homeFeedUseCase.onRefresh()
        }
    }
}