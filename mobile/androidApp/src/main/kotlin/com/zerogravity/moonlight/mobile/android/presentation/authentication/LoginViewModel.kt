package com.zerogravity.moonlight.mobile.android.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.usecase.LoginUiEvent
import com.zerogravity.moonlight.mobile.common.domain.usecase.LoginUseCase
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

const val LoginViewModelTag = "LoginViewModel"

class LoginViewModel(private val appLogger: AppLogger) : ViewModel(), KoinScopeComponent {

    override val scope: Scope
        get() = createScope(this)

    private val loginUseCase by lazy {
        LoginUseCase(viewModelScope)
    }

    val loginUiState = loginUseCase.uiState

    fun onAuthenticationUiEvent(loginUiEvent: LoginUiEvent) {
        appLogger.d("$LoginViewModelTag: loginUiEvent: $loginUiEvent")
        loginUseCase.onAuthenticationUiEvent(loginUiEvent)
    }
}