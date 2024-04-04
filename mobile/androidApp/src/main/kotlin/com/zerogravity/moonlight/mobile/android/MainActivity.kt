package com.zerogravity.moonlight.mobile.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.zerogravity.moonlight.mobile.android.presentation.App
import com.zerogravity.moonlight.mobile.common.data.repository.AuthenticationRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val authenticationRepository: AuthenticationRepository by inject()
        lifecycleScope.launch {
            authenticationRepository.isAuthenticated().collect {
                setContent {
                    App(
                        calculateWindowSizeClass(this@MainActivity),
                        isAuthenticated = it
                    )
                }
            }
        }
    }
}