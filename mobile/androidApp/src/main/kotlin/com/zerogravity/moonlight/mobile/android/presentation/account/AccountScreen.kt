package com.zerogravity.moonlight.mobile.android.presentation.account

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zerogravity.moonlight.mobile.android.R
import com.zerogravity.moonlight.mobile.android.presentation.authentication.LoginViewModel
import com.zerogravity.moonlight.mobile.android.presentation.authentication.GoogleAndroidAuthProvider
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.LoadingWheel
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.TopAppBar
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.usecase.LoginUiEvent
import com.zerogravity.moonlight.mobile.common.domain.usecase.LoginUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.get


@Composable
fun AccountRoute(
    modifier: Modifier = Modifier,
    appLogger: AppLogger = KoinJavaComponent.get(AppLogger::class.java),
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.loginUiState.collectAsStateWithLifecycle()
    val googleAndroidAuthProvider =
        get<GoogleAndroidAuthProvider>(GoogleAndroidAuthProvider::class.java)
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    AccountScreen(modifier, uiState, appLogger, onUiEvent = {
        coroutineScope.launch {
            googleAndroidAuthProvider.signOut(activity)
            viewModel.onAuthenticationUiEvent(it)
        }
    })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    loginUiState: LoginUiState,
    appLogger: AppLogger = KoinJavaComponent.get(AppLogger::class.java),
    onUiEvent: (LoginUiEvent) -> Unit
) {

    appLogger.d("HomeFeedScreen: Entry to HomeFeedScreen")
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }


    val appName = stringResource(id = R.string.app_name)

    Scaffold(
        topBar = {
            TopAppBar(titleRes = R.string.app_name,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.semantics { contentDescription = appName })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .wrapContentSize(Alignment.Center)

        ) {
            if (loginUiState.inProgress) {
                LoadingWheel(contentDesc = "Please wait")
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .wrapContentSize(Alignment.Center)
            ) {
                if (loginUiState.isLoggedIn) {
                    Button(onClick = {
                        onUiEvent(LoginUiEvent.Logout)
                    }) {
                        Text("Logout")
                    }
                }
            }

        }
    }


}
