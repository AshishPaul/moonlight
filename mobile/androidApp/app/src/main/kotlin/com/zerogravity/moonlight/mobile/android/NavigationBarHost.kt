package com.zerogravity.moonlight.mobile.android

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zerogravity.moonlight.mobile.core.ui.navigation.NavigationBarItem
import com.zerogravity.moonlight.mobile.core.ui.navigation.NavigationDestination
import com.zerogravity.moonlight.mobile.core.ui.navigation.TopLevelDestination
import com.zerogravity.moonlight.mobile.core.ui.navigation.screenFadeIn
import com.zerogravity.moonlight.mobile.core.ui.navigation.screenFadeOut
import com.zerogravity.moonlight.mobile.core.ui.navigation.screenSlideIn
import com.zerogravity.moonlight.mobile.core.ui.navigation.screenSlideOut
import com.zerogravity.moonlight.mobile.features.account.accountGraph
import com.zerogravity.moonlight.mobile.features.home.HomeFeedDestination
import com.zerogravity.moonlight.mobile.features.home.homeFeedGraph
import com.zerogravity.moonlight.mobile.features.services.ServiceListDestination
import com.zerogravity.moonlight.mobile.features.services.serviceListGraph

object NavigationBarHostDestination : NavigationDestination {
    override val route = "navigation_bar_host_route"
}

internal fun NavGraphBuilder.navigationBarHost(
    appState: AppState,
    onNavigateToDestination: (NavigationDestination, String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(NavigationBarHostDestination.route) {

        Scaffold(containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                BottomBar(
                    navBarItems = appState.navBarItems,
                    onNavigateToDestination = {
                        appState.navigate(it, it.route)
                    },
                    currentDestination = appState.currentDestination
                )
            }) { padding ->

            NavHost(
                navController = rememberNavController(),
                startDestination = HomeFeedDestination.graphRoute,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding),
                enterTransition = { screenSlideIn() },
                exitTransition = { screenFadeOut() },
                popEnterTransition = { screenFadeIn() },
                popExitTransition = { screenSlideOut() },
            ) {
                homeFeedGraph(onCategoryClick = {
                    onNavigateToDestination(
                        ServiceListDestination,
                        ServiceListDestination.createNavigationRoute(it)
                    )
                })
                serviceListGraph(onBackClick = onBackClick)

                accountGraph { }
            }
        }
    }
}

@Composable
internal fun BottomBar(
    navBarItems: List<NavigationBarItem>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        contentColor = NavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
    ) {
        navBarItems.forEach { navBarItem ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == navBarItem.navigationDestination.route } == true
            NavigationBarItem(selected = selected,
                onClick = { onNavigateToDestination(navBarItem.navigationDestination) },
                icon = {
                    val icon = if (selected) {
                        navBarItem.selectedIcon
                    } else {
                        navBarItem.unselectedIcon
                    }
                    Icon(icon, contentDescription = stringResource(navBarItem.iconTextId))
                },
                label = { Text(stringResource(navBarItem.iconTextId)) })
        }
    }
}

object NavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}