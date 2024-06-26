package com.zerogravity.moonlight.mobile.features.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.zerogravity.moonlight.mobile.core.ui.navigation.NavigationDestination
import com.zerogravity.moonlight.mobile.core.ui.navigation.TopLevelDestination

object LoginDestination : TopLevelDestination {
    override val route = "login_route"
    override val graphRoute = "login_graph_route"
}

fun NavGraphBuilder.loginGraph(navigateTo: (NavigationDestination, String) -> Unit) {
    navigation(
        startDestination = LoginDestination.route,
        route = LoginDestination.graphRoute,
//        deepLinks = listOf(
//            navDeepLink { uriPattern = LOGIN_DEEPLINK }
//        )
    ) {
        loginRoute(navigateTo)
    }

}
