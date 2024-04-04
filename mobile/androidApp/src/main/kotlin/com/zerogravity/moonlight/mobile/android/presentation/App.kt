package com.zerogravity.moonlight.mobile.android.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.zerogravity.moonlight.mobile.android.presentation.authentication.LoginDestination
import com.zerogravity.moonlight.mobile.android.presentation.homefeed.HomeFeedDestination
import com.zerogravity.moonlight.mobile.android.presentation.ui.AppTheme
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.AppBackground
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.AppNavHost
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.TopLevelDestination
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import org.koin.java.KoinJavaComponent.get

@Composable
fun App(
    windowSizeClass: WindowSizeClass,
    appState: AppState = rememberAppState(windowSizeClass),
    appLogger: AppLogger = get(AppLogger::class.java),
    isAuthenticated: Boolean
) {

//    LaunchedEffect(key1 = isAuthenticated) {
//        appLogger.d("App, LaunchedEffect: isAuthenticated: $isAuthenticated")
//        val startRoute = if (!isAuthenticated) {
//            LoginDestination.route
//        } else {
//            HomeFeedDestination.route
//        }
//        appState.navController.navigate(startRoute) {
//            popUpTo(startRoute) {
//                inclusive = true
//            }
//        }
//    }
    AppTheme {
        AppBackground {
            Scaffold(containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                bottomBar = {
                    if (appState.shouldShowBottomBar) {
                        BottomBar(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigate,
                            currentDestination = appState.currentDestination
                        )
                    }
                }) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {

                    val startRoute = if (!isAuthenticated) {
                        LoginDestination.route
                    } else {
                        HomeFeedDestination.route
                    }
                    appLogger.d("App : creating AppNavHost with startRoute $startRoute")
                    AppNavHost(
                        navController = appState.navController,
                        onBackClick = appState::onBackClick,
                        onNavigateToDestination = appState::navigate,
                        modifier = Modifier
                            .padding(padding)
                            .consumeWindowInsets(padding),
                        startDestination = startRoute
                    )

                }
            }
        }
    }

}

@Composable
private fun BottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        contentColor = NavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
    ) {
        destinations.forEach { destination ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationBarItem(selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    Icon(icon, contentDescription = stringResource(destination.iconTextId))
                },
                label = { Text(stringResource(destination.iconTextId)) })
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
