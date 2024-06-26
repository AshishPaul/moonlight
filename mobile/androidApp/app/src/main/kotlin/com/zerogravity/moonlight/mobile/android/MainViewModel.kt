package com.zerogravity.moonlight.mobile.android

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerogravity.moonlight.mobile.common.data.repository.AuthenticationRepository
import com.zerogravity.moonlight.mobile.common.data.repository.UserPreferenceRepository
import com.zerogravity.moonlight.mobile.common.domain.WhileUiSubscribed
import com.zerogravity.moonlight.mobile.common.domain.model.UserPreferences
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    authenticationRepository: AuthenticationRepository,
    userPreferenceRepository: UserPreferenceRepository,
    val handle: SavedStateHandle
) : ViewModel() {

    val mainUiState = combine(
        authenticationRepository.isAuthenticated(),
        userPreferenceRepository.userPreferences
    ) { isAuthenticate, userPreference ->
        MainUiState.Loaded(isAuthenticate, userPreference)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainUiState.Loading,
        started = WhileUiSubscribed
    )

    sealed class MainUiState {
        data class Loaded(
            val isAuthenticated: Boolean,
            val userPreferences: UserPreferences
        ) : MainUiState()

        data object Loading : MainUiState()
    }

    companion object {
        private const val LogTag = "MainViewModel"
    }
}