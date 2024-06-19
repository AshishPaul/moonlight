package com.zerogravity.moonlight.mobile.android.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zerogravity.moonlight.mobile.android.presentation.AppState
import com.zerogravity.moonlight.mobile.android.presentation.account.accountGraph
import com.zerogravity.moonlight.mobile.android.presentation.authentication.LoginDestination
import com.zerogravity.moonlight.mobile.android.presentation.authentication.loginGraph
import com.zerogravity.moonlight.mobile.android.presentation.authentication.signUpGraph
import com.zerogravity.moonlight.mobile.android.presentation.homefeed.HomeFeedDestination
import com.zerogravity.moonlight.mobile.android.presentation.homefeed.homeFeedGraph
import com.zerogravity.moonlight.mobile.android.presentation.services.ServiceListDestination
import com.zerogravity.moonlight.mobile.android.presentation.services.serviceListGraph

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