package com.zerogravity.moonlight.mobile.android.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onNavigateToDestination: (NavigationDestination, String) -> Unit = { _, _ -> },
    onBackClick: () -> Unit = {},
    startDestination: String = HomeFeedDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        signUpGraph {
            navController.navigate(LoginDestination.route) {
                popUpTo(LoginDestination.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }

        loginGraph { navDestination, route ->
            onNavigateToDestination(navDestination,route)
        }

        homeFeedGraph(navigateTo = {
            onNavigateToDestination(
                ServiceListDestination,
                ServiceListDestination.createNavigationRoute(it)
            )
        })
        serviceListGraph(onBackClick = onBackClick)

        accountGraph { }
    }
}