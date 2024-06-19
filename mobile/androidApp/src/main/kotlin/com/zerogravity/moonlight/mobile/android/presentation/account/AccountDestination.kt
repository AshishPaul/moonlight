package com.zerogravity.moonlight.mobile.android.presentation.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.zerogravity.moonlight.mobile.android.presentation.services.ServiceListDestination
import com.zerogravity.moonlight.mobile.android.presentation.services.serviceListRoute
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationDestination
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.TopLevelDestination

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
