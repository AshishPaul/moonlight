package com.zerogravity.moonlight.mobile.android.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationDestination
import org.koin.androidx.compose.koinViewModel

object SignUpDestination : NavigationDestination {
    override val route = "signup_route"
    override val destination = "signup_destination"
}

fun NavGraphBuilder.signUpGraph(onSignUpSuccess: () -> Unit) {
    composable(route = SignUpDestination.route) {
        val viewModel: SignUpViewModel = koinViewModel()
        val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()

        if (signUpUiState.success) {
            onSignUpSuccess()
        } else {
            SignUpScreen(
                signUpUiState = signUpUiState,
            ) {
                viewModel.onSignUpUiEvent(it)
            }
        }
    }
}
