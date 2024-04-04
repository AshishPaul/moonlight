package com.zerogravity.moonlight.mobile.android.presentation.authentication

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationDestination
import org.koin.androidx.compose.koinViewModel

object LoginDestination : NavigationDestination {
    override val route = "login_route"
    override val destination = "login_destination"
}

fun NavGraphBuilder.loginGraph(navigateTo: (NavigationDestination, String) -> Unit) {
    composable(route = LoginDestination.route) {
        val viewModel: LoginViewModel = koinViewModel()
        val authenticationUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
        LoginScreen(
            loginUiState = authenticationUiState,
            onAuthenticationUiEvent = {
                viewModel.onAuthenticationUiEvent(it)
            },
            onSignUpClicked = {
                navigateTo(SignUpDestination, SignUpDestination.route)
            }
        )
    }
}
