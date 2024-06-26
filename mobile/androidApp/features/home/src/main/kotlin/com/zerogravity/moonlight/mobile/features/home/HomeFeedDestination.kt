package com.zerogravity.moonlight.mobile.features.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.zerogravity.moonlight.mobile.core.ui.navigation.TopLevelDestination

object HomeFeedDestination : TopLevelDestination {
    override val route = "home_feed_route"
    override val graphRoute = "home_feed_graph_route"
}

fun NavGraphBuilder.homeFeedGraph(onCategoryClick: (String) -> Unit) {
    navigation(
        startDestination = HomeFeedDestination.route,
        route = HomeFeedDestination.graphRoute,
//        deepLinks = listOf(
//            navDeepLink { uriPattern = HOME_DEEPLINK }
//        ),
    ) {
        homeFeedRoute(onCategoryClick = onCategoryClick)
    }

}
