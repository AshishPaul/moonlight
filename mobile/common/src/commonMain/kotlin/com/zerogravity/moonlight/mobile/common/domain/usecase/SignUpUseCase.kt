package com.zerogravity.moonlight.mobile.common.domain.usecase

import com.zerogravity.moonlight.mobile.common.data.repository.AuthenticationRepository
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.WhileUiSubscribed
import com.zerogravity.moonlight.mobile.common.domain.error.AppError
import com.zerogravity.moonlight.mobile.common.domain.error.toAppError
import com.zerogravity.moonlight.shared.domain.model.request.UserRequest
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class SignUpUiState(
    val inProgress: Boolean = false,
    val success: Boolean = false,
    val error: AppError? = null,
)

sealed class SignUpUiEvent {
    data class SignUp(val userRequest: UserRequest) : SignUpUiEvent()
}

const val SignUpUseCaseTag = "SignUpUseCase"

class SignUpUseCase(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main),
) : KoinComponent {

    private val authenticationRepository: AuthenticationRepository by inject()
    private val appLogger: AppLogger by inject()

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        coroutineScope.launch {
            appLogger.d("$SignUpUseCaseTag: exceptionHandler : ${exception.message}")
            error.emit(exception.toAppError())
        }
    }

    private val progress = MutableStateFlow(false)

    private val success = MutableStateFlow(false)

    private val error = MutableStateFlow<AppError?>(null)

    private var signUpUiState = SignUpUiState()

    val uiState = combine(
        progress,
        success,
        error
    ) { progress, success, error ->

        signUpUiState = signUpUiState.copy(
            inProgress = progress,
            success = success,
            error = error,
        ).apply {
            appLogger.d("$SignUpUseCaseTag: combine: AuthenticationUiState : $this")
        }
        signUpUiState
    }.stateIn(
        scope = coroutineScope, started = WhileUiSubscribed, initialValue = signUpUiState
    )

    fun onUiEvent(signUpUiEvent: SignUpUiEvent) {
        appLogger.d("$SignUpUseCaseTag: signUpRequest: $signUpUiEvent")
        when (signUpUiEvent) {
            is SignUpUiEvent.SignUp -> signUp(signUpUiEvent.userRequest)
        }
    }

    private fun signUp(userRequest: UserRequest) {
        coroutineScope.launch(exceptionHandler) {
            progress.emit(true)
            authenticationRepository.signUp(userRequest)
            progress.emit(false)
            success.emit(true)
        }
    }
}
