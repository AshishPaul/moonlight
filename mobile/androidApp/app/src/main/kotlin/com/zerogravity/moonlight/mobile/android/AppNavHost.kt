package com.zerogravity.moonlight.mobile.android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.zerogravity.moonlight.mobile.features.authentication.LoginDestination
import com.zerogravity.moonlight.mobile.features.authentication.loginGraph
import com.zerogravity.moonlight.mobile.features.authentication.signUpGraph
import com.zerogravity.moonlight.mobile.features.home.HomeFeedDestination

@Composable
fun AppNavHost(
    appState: AppState,
    startRoute: String = HomeFeedDestination.graphRoute
) {
    NavHost(
        navController = appState.navController,
        startDestination = startRoute,
    ) {
        navigationBarHost(
            appState = appState,
            onNavigateToDestination = appState::navigate,
            onBackClick = appState::onBackClick
        )
        signUpGraph {
            appState.navigate(LoginDestination, LoginDestination.route)
        }

        loginGraph(appState::navigate)
    }
}