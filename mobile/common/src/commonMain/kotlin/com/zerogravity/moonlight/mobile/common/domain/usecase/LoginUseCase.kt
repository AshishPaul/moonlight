package com.zerogravity.moonlight.mobile.common.domain.usecase

import com.zerogravity.moonlight.mobile.common.data.repository.AuthenticationRepository
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.WhileUiSubscribed
import com.zerogravity.moonlight.mobile.common.domain.error.AppError
import com.zerogravity.moonlight.mobile.common.domain.error.toAppError
import com.zerogravity.moonlight.mobile.common.domain.model.AuthResult
import com.zerogravity.moonlight.mobile.common.domain.model.toAppError
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

data class LoginUiState(
    val isLoggedIn: Boolean = false,
    val inProgress: Boolean = false,
    val error: AppError? = null,
)

sealed class LoginUiEvent {
    data class LoginWithEmailPassword(val email: String, val password: String) : LoginUiEvent()
    data class LoginWithGoogle(val authResult: AuthResult) : LoginUiEvent()
    data object Logout : LoginUiEvent()
}

const val LoginUseCaseTag = "LoginUseCase"

class LoginUseCase(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
) : KoinComponent {

    private val authenticationRepository: AuthenticationRepository by inject()
    private val appLogger: AppLogger by inject()

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        coroutineScope.launch {
            appLogger.d("$LoginUseCaseTag: exceptionHandler : ${exception.message}")
            error.emit(exception.toAppError())
            progress.emit(false)
        }
    }

    private val isLoggedIn: Flow<Boolean> = authenticationRepository.isAuthenticated()

    private val progress = MutableStateFlow(false)

    private val error = MutableStateFlow<AppError?>(null)

    private var loginUiState = LoginUiState()

    val uiState = combine(
        isLoggedIn,
        progress,
        error,
    ) { isLoggedIn, progress, error ->

        loginUiState = loginUiState.copy(
            isLoggedIn = isLoggedIn,
            inProgress = progress,
            error = error
        ).apply {
            appLogger.d("$LoginUseCaseTag: combine: AuthenticationUiState : $this")
        }
        loginUiState
    }.stateIn(
        scope = coroutineScope, started = WhileUiSubscribed, initialValue = loginUiState
    )

    fun onAuthenticationUiEvent(loginUiEvent: LoginUiEvent) {
        appLogger.d("$LoginUseCaseTag: loginUiEvent: $loginUiEvent")
        when (loginUiEvent) {
            is LoginUiEvent.LoginWithEmailPassword -> {
                loginWithEmailPassword(loginUiEvent)
            }

            is LoginUiEvent.LoginWithGoogle -> {
                loginWithAuthProvider(loginUiEvent.authResult)
            }

            LoginUiEvent.Logout -> {
                logout()
            }
        }
    }

    private fun loginWithEmailPassword(loginUiEvent: LoginUiEvent.LoginWithEmailPassword) {
        coroutineScope.launch(exceptionHandler) {
            progress.emit(true)
            authenticationRepository.authenticateWithEmailPassword(
                loginUiEvent.email,
                loginUiEvent.password
            )
            progress.emit(false)
        }
    }

    private fun loginWithAuthProvider(authResult: AuthResult) {
        coroutineScope.launch(exceptionHandler) {
            when (authResult) {
                is AuthResult.Successful -> {
                    progress.emit(true)
                    authenticationRepository.authenticateWithAuthProvider(authResult.authData)
                    progress.emit(false)
                }

                is AuthResult.Error -> {
                    error.emit(authResult.authError.toAppError())
                }
            }
        }
    }

    private fun logout() {
        coroutineScope.launch(exceptionHandler) {
            progress.emit(true)
            authenticationRepository.logout()
            progress.emit(false)
        }
    }
}
