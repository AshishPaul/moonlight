package com.zerogravity.moonlight.mobile.android.presentation.services

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationDestination

object ServiceListDestination : NavigationDestination {
    const val categoryArg = "categoryId"
    override val route = "category/{$categoryArg}/service"
    override val destination = "service_list_destination"

    fun createNavigationRoute(categoryArg: String): String {
        val encodedId = Uri.encode(categoryArg)
        return "category/{$encodedId}/service"
    }

    fun fromNavArgs(entry: NavBackStackEntry): String {
        val encodedId = entry.arguments?.getString(categoryArg)!!
        return Uri.decode(encodedId)
    }
}


// can test using following (needs to be updated as astronauts change!)
// adb shell am start -d "peopleinspace://person/Samantha%20Cristoforetti" -a android.intent.action.VIEW
fun NavGraphBuilder.serviceListGraph(onBackClick: () -> Unit) {
    composable(
        route = ServiceListDestination.route,
        arguments = listOf(
            navArgument(ServiceListDestination.categoryArg) { type = NavType.StringType }
        ),
        deepLinks = listOf(navDeepLink {
            uriPattern = "moonlight://category/{${ServiceListDestination.categoryArg}}/service"
        })
    ) {
        ServiceListRoute(onBackClick)
    }
}