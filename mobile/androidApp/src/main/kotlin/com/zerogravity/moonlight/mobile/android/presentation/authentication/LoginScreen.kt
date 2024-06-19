@file:OptIn(ExperimentalMaterial3Api::class)

package com.zerogravity.moonlight.mobile.android.presentation.authentication

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zerogravity.moonlight.mobile.android.R
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.EmailInputField
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.IconWithButton
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.LoadingWheel
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.PasswordInputField
import com.zerogravity.moonlight.mobile.android.presentation.ui.navigation.NavigationDestination
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.model.AuthProvider
import com.zerogravity.moonlight.mobile.common.domain.usecase.LoginUiEvent
import com.zerogravity.moonlight.mobile.common.domain.usecase.LoginUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.get

const val LoginTag = "Login"


fun NavGraphBuilder.loginRoute(navigateTo: (NavigationDestination, String) -> Unit) {
    composable(route = LoginDestination.route) {
        val viewModel: LoginViewModel = koinViewModel()
        val authenticationUiState by viewModel.loginUiState.collectAsStateWithLifecycle()
        LoginScreen(
            loginUiState = authenticationUiState,
            onAuthenticationUiEvent = {
                viewModel.onAuthenticationUiEvent(it)
            },
            onSignUpClicked = {
                navigateTo(SignUpDestination, SignUpDestination.route)
            }
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    appLogger: AppLogger = get(AppLogger::class.java),
    loginUiState: LoginUiState,
    googleAndroidAuthProvider: GoogleAndroidAuthProvider = get(GoogleAndroidAuthProvider::class.java),
    onAuthenticationUiEvent: (LoginUiEvent) -> Unit,
    onSignUpClicked: () -> Unit
) {
    appLogger.d("$LoginTag: Start LoginScreen : authenticationUiState: $loginUiState")

    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity

    LoginContent(modifier = modifier,
        loginUiState,
        onSignUpClicked = onSignUpClicked,
        onLoginWithCredentials = { email, password ->
            onAuthenticationUiEvent(
                LoginUiEvent.LoginWithEmailPassword(
                    email, password
                )
            )
        },
        onLoginWithProvider = {
            coroutineScope.launch {
                val authResult = googleAndroidAuthProvider.login(activity)
                onAuthenticationUiEvent(
                    LoginUiEvent.LoginWithGoogle(
                        authResult
                    )
                )
            }
        })
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    loginUiState: LoginUiState,
    onSignUpClicked: () -> Unit,
    onLoginWithCredentials: (String, String) -> Unit,
    onLoginWithProvider: (AuthProvider) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
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
                .padding(innerPadding)
        ) {
            if (loginUiState.inProgress) {
                LoadingWheel(contentDesc = "Please wait")
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                LoginScreenHeader()

                LoginWithCredentialsView(onLoginWithCredentials)

                Spacer(modifier = Modifier.height(24.dp))

                SignUpOption(onSignUpClicked)

                Spacer(modifier = Modifier.height(84.dp))

                LoginViaProvidersView(onLoginWithProvider)

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun LoginScreenHeader() {
    Image(
        modifier = Modifier.padding(top = 44.dp, bottom = 16.dp),
        painter = painterResource(id = R.drawable.ic_app_logo),
        contentDescription = "Login App Logo"
    )

    Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "Welcome!", fontSize = 32.sp
    )
}


@Composable
private fun LoginWithCredentialsView(
    onLoginWithCredentials: (String, String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "Hi, Enter your details to sign into your account!",
        fontSize = 16.sp
    )

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }


    EmailInputField(localFocusManager, email) {
        email = it
    }

    PasswordInputField(localFocusManager, keyboardController, password) {
        password = it
    }

    Button(modifier = Modifier.padding(0.dp, 6.dp), onClick = {
        onLoginWithCredentials(email, password)
    }) {
        Text(
            modifier = Modifier.padding(10.dp, 5.dp), style = TextStyle(
                fontSize = 14.sp
            ), text = "Login"
        )
    }
}


@Composable
private fun SignUpOption(onSignUpClicked: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Don't have an account with us? ",
            fontSize = 16.sp
        )
        OutlinedButton(
            onClick = onSignUpClicked,

            ) {
            Text(
                style = TextStyle(
                    fontSize = 14.sp
                ), text = "Sign-up"
            )
        }
    }
}

@Composable
private fun LoginViaProvidersView(
    onLoginButtonClicked: (AuthProvider) -> Unit
) {
    Text(
        text = "Or, Sign in via",
        modifier = Modifier.padding(top = 12.dp),
        fontSize = 16.sp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        IconWithButton(
            modifier = Modifier.weight(1f),
            iconResource = R.drawable.ic_google_filled_color,
            contentDescription = "Google login button"
        ) {
            onLoginButtonClicked(AuthProvider.GOOGLE)
        }
        Divider(modifier = Modifier.width(24.dp), color = Color.Transparent)
        IconWithButton(
            modifier = Modifier.weight(1f),
            iconResource = R.drawable.ic_apple_filled_color,
            contentDescription = "Apple login button"
        ) {
            onLoginButtonClicked(AuthProvider.APPLE)
        }
        Divider(modifier = Modifier.width(24.dp), color = Color.Transparent)

        IconWithButton(
            modifier = Modifier.weight(1f),
            iconResource = R.drawable.ic_facebook_outlined,
            contentDescription = "Facebook login button"
        ) {
            onLoginButtonClicked(AuthProvider.FACEBOOK)
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginContent(
        loginUiState = LoginUiState(),
        onSignUpClicked = {

        },
        onLoginWithCredentials = { _, _ ->

        },
        onLoginWithProvider = {

        }
    )
}