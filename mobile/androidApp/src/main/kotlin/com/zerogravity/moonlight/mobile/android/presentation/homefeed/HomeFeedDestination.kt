package com.zerogravity.moonlight.mobile.android.presentation.homefeed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationDestination

object HomeFeedDestination : NavigationDestination {
    override val route = "home_feed_route"
    override val destination = "home_feed_destination"
}

fun NavGraphBuilder.homeFeedGraph(navigateTo: (String) -> Unit) {
    composable(route = HomeFeedDestination.route) {
        HomeFeedRoute(navigateTo = navigateTo)
    }
}
