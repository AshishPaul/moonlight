package com.zerogravity.moonlight.mobile.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserDataStore
import com.zerogravity.moonlight.mobile.features.authentication.LoginDestination
import com.zerogravity.moonlight.mobile.common.data.repository.AuthenticationRepository
import com.zerogravity.moonlight.mobile.common.domain.model.DarkThemeConfig
import com.zerogravity.moonlight.mobile.core.ui.AppTheme
import com.zerogravity.moonlight.mobile.core.ui.components.AppBackground
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        val authenticationRepository: AuthenticationRepository by inject()
//        val userDataStore: UserDataStore by inject()

        var uiState: MainViewModel.MainUiState by mutableStateOf(MainViewModel.MainUiState.Loading)

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mainUiState
                    .onEach { uiState = it }
                    .collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (uiState) {
                MainViewModel.MainUiState.Loading -> true
                is MainViewModel.MainUiState.Loaded -> false
            }
        }

        lifecycleScope.launch {
            authenticationRepository.isAuthenticated().collect {
                // Turn off the decor fitting system windows, which allows us to handle insets,
                // including IME animations, and go edge-to-edge
                // This also sets up the initial system bar style based on the platform theme
                enableEdgeToEdge()
                setContent {

//                    val darkTheme = shouldUseDarkTheme(uiState)
//
//                    // Update the edge to edge configuration to match the theme
//                    // This is the same parameters as the default enableEdgeToEdge call, but we manually
//                    // resolve whether or not to show dark theme using uiState, since it can be different
//                    // than the configuration's dark theme value based on the user preference.
//                    DisposableEffect(darkTheme) {
//                        enableEdgeToEdge(
//                            statusBarStyle = SystemBarStyle.auto(
//                                android.graphics.Color.TRANSPARENT,
//                                android.graphics.Color.TRANSPARENT,
//                            ) { darkTheme },
//                            navigationBarStyle = SystemBarStyle.auto(
//                                lightScrim,
//                                darkScrim,
//                            ) { darkTheme },
//                        )
//                        onDispose {}
//                    }

                    AppTheme {
                        AppBackground {
                            val startRoute = if (!it) {
                                LoginDestination.graphRoute
                            } else {
                                NavigationBarHostDestination.route
                            }
                            AppNavHost(
                                appState = rememberAppState(calculateWindowSizeClass(this@MainActivity)),
                                startRoute
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Returns `true` if dark theme should be used, as a function of the [uiState] and the
 * current system context.
 */
@Composable
private fun shouldUseDarkTheme(
    uiState: MainViewModel.MainUiState,
): Boolean = when (uiState) {
    MainViewModel.MainUiState.Loading -> isSystemInDarkTheme()
    is MainViewModel.MainUiState.Loaded -> when (uiState.userPreferences.darkThemeConfig) {
        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
        DarkThemeConfig.LIGHT -> false
        DarkThemeConfig.DARK -> true
    }
}