package com.zerogravity.moonlight.mobile.features.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.usecase.SignUpUiEvent
import com.zerogravity.moonlight.mobile.common.domain.usecase.SignUpUseCase
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

const val SignUpViewModelTag = "SignUpViewModel"

class SignUpViewModel(private val appLogger: AppLogger) : ViewModel(), KoinScopeComponent {

    override val scope: Scope
        get() = createScope(this)

    private val signUpUseCase by lazy {
        SignUpUseCase(viewModelScope)
    }

    val signUpUiState = signUpUseCase.uiState

    fun onSignUpUiEvent(signUpUiEvent: SignUpUiEvent) {
        appLogger.d("$SignUpViewModelTag: signUpUiEvent: $signUpUiEvent")
        signUpUseCase.onUiEvent(signUpUiEvent)
    }
}