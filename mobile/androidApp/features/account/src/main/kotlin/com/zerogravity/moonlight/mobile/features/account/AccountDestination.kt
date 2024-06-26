package com.zerogravity.moonlight.mobile.features.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.zerogravity.moonlight.mobile.core.ui.navigation.TopLevelDestination

object AccountDestination : TopLevelDestination {
    override val route = "account_route"
    override val graphRoute = "account_graph_route"
}

fun NavGraphBuilder.accountGraph(navigateTo: (String) -> Unit) {
    navigation(
        startDestination = AccountDestination.route,
        route = AccountDestination.graphRoute,
//        deepLinks = listOf(
//            navDeepLink { uriPattern = HOME_DEEPLINK }
//        ),
    ) {
        accountRoute()
    }


}
