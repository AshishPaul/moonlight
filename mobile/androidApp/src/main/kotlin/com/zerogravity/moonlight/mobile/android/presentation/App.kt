package com.zerogravity.moonlight.mobile.android.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.zerogravity.moonlight.mobile.android.presentation.account.accountGraph
import com.zerogravity.moonlight.mobile.android.presentation.authentication.LoginDestination
import com.zerogravity.moonlight.mobile.android.presentation.authentication.loginGraph
import com.zerogravity.moonlight.mobile.android.presentation.authentication.signUpGraph
import com.zerogravity.moonlight.mobile.android.presentation.homefeed.HomeFeedDestination
import com.zerogravity.moonlight.mobile.android.presentation.homefeed.homeFeedGraph
import com.zerogravity.moonlight.mobile.android.presentation.services.ServiceListDestination
import com.zerogravity.moonlight.mobile.android.presentation.services.serviceListGraph
import com.zerogravity.moonlight.mobile.android.presentation.ui.AppTheme
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.AppBackground
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.AppNavHost
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationBarHostDestination
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.navigationBarHost
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import org.koin.java.KoinJavaComponent.get

@Composable
fun App(
    windowSizeClass: WindowSizeClass,
    appState: AppState = rememberAppState(windowSizeClass),
    appLogger: AppLogger = get(AppLogger::class.java),
    isAuthenticated: Boolean
) {
    AppTheme {
        AppBackground {
            val startRoute = if (!isAuthenticated) {
                LoginDestination.route
            } else {
                NavigationBarHostDestination.route
            }
            AppNavHost(appState = appState, startRoute)
        }
    }
}




