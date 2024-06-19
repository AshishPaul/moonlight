package com.zerogravity.moonlight.mobile.android.presentation.services

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.TopLevelDestination

object ServiceListDestination : TopLevelDestination {
    const val categoryArg = "categoryId"
    override val route = "service_list_route"
    override val graphRoute = "service_list_graph_route"

    fun createNavigationRoute(categoryArg: String): String {
        val encodedId = Uri.encode(categoryArg)
        return "category/{$encodedId}/service"
    }

    fun fromNavArgs(entry: NavBackStackEntry): String {
        val encodedId = entry.arguments?.getString(categoryArg)!!
        return Uri.decode(encodedId)
    }
}


fun NavGraphBuilder.serviceListGraph(onBackClick: () -> Unit) {

    navigation(
        startDestination = ServiceListDestination.route,
        route = ServiceListDestination.graphRoute,
//        deepLinks = listOf(
//            navDeepLink { uriPattern = HOME_DEEPLINK }
//        ),
    ) {
        serviceListRoute(onBackClick = onBackClick)
    }


}