package com.zerogravity.moonlight.mobile.android

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zerogravity.moonlight.mobile.core.ui.navigation.NavigationBarItem
import com.zerogravity.moonlight.mobile.core.ui.navigation.NavigationDestination
import com.zerogravity.moonlight.mobile.core.ui.navigation.TopLevelDestination
import com.zerogravity.moonlight.mobile.features.account.AccountDestination
import com.zerogravity.moonlight.mobile.features.home.HomeFeedDestination

@Composable
fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): AppState {
    return remember(navController, windowSizeClass) {
        AppState(navController, windowSizeClass)
    }
}


@Stable
class AppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() = navBarItems.any {
            navController
                .currentBackStackEntryAsState().value?.destination?.route.equals(it.navigationDestination.route)
        }


//    val shouldShowBottomBar: Boolean
//        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
//            windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    /**
     * Top level destinations to be used in the BottomBar and NavRail
     */
    val navBarItems: List<NavigationBarItem> = listOf(
        NavigationBarItem(
            navigationDestination = HomeFeedDestination,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            iconTextId = com.zerogravity.moonlight.mobile.features.home.R.string.home_feed
        ),
        NavigationBarItem(
            navigationDestination = AccountDestination,
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            iconTextId = com.zerogravity.moonlight.mobile.features.account.R.string.account
        ),
    )


    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param destination: The [NavigationDestination] the app needs to navigate to.
     * @param route: Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: NavigationDestination, route: String? = null) {
        if (destination is TopLevelDestination) {
            navController.navigate(route ?: destination.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        } else {
            navController.navigate(route ?: destination.route)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}

