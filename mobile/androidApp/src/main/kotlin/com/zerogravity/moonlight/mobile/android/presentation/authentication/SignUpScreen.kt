@file:OptIn(ExperimentalMaterial3Api::class)

package com.zerogravity.moonlight.mobile.android.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zerogravity.moonlight.mobile.android.R
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.InputField
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.LoadingWheel
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.PasswordInputField
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.usecase.SignUpUiEvent
import com.zerogravity.moonlight.mobile.common.domain.usecase.SignUpUiState
import com.zerogravity.moonlight.shared.domain.model.request.UserRequest
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent.get

const val SignUpTag = "SignUp"

fun NavGraphBuilder.signUpRoute(onSignUpSuccess: () -> Unit) {
    composable(route = SignUpDestination.route) {
        val viewModel: SignUpViewModel = koinViewModel()
        val signUpUiState by viewModel.signUpUiState.collectAsStateWithLifecycle()

        if (signUpUiState.success) {
            onSignUpSuccess()
        } else {
            SignUpScreen(
                signUpUiState = signUpUiState,
            ) {
                viewModel.onSignUpUiEvent(it)
            }
        }
    }
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    appLogger: AppLogger = get(AppLogger::class.java),
    signUpUiState: SignUpUiState,
    onSignUpUiEvent: (SignUpUiEvent) -> Unit
) {
    appLogger.d("$SignUpTag: Start SignUpScreen")

    SignUpContent(modifier = modifier,
        signUpUiState,
        onSignUpClicked = {
            onSignUpUiEvent(SignUpUiEvent.SignUp(it))
        }
    )
}

@Composable
private fun SignUpContent(
    modifier: Modifier = Modifier,
    signUpUiState: SignUpUiState,
    onSignUpClicked: (UserRequest) -> Unit,
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
            if (signUpUiState.inProgress) {
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

                Image(
                    modifier = Modifier.padding(top = 44.dp, bottom = 16.dp),
                    painter = painterResource(id = R.drawable.ic_app_logo),
                    contentDescription = "Login App Logo"
                )

                SignUpView(onSignUpClicked)

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Composable
private fun SignUpView(
    onSignUpClicked: (UserRequest) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    Text(
        modifier = Modifier.padding(0.dp, 16.dp),
        text = "Hi, Enter your details to create your account!",
        fontSize = 16.sp
    )

    var givenName by rememberSaveable { mutableStateOf("") }
    var familyName by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var profilePictureUrl by rememberSaveable { mutableStateOf("") }

    InputField(
        focusManager = localFocusManager,
        text = givenName,
        hintText = "Given name",
        leadingIcon = Icons.Outlined.AccountCircle,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            givenName = it
        }
    )

    InputField(
        focusManager = localFocusManager,
        text = familyName,
        hintText = "Family name",
        leadingIcon = Icons.Outlined.Email,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            familyName = it
        }
    )

    InputField(
        focusManager = localFocusManager,
        text = phoneNumber,
        hintText = "Phone number",
        leadingIcon = Icons.Outlined.Email,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            phoneNumber = it
        }
    )


    InputField(
        focusManager = localFocusManager,
        text = email,
        hintText = "Email",
        leadingIcon = Icons.Outlined.Email,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            email = it
        }
    )

    PasswordInputField(
        localFocusManager,
        keyboardController,
        password,
        hintText = "Password"
    ) {
        password = it
    }

    PasswordInputField(
        localFocusManager,
        keyboardController,
        confirmPassword,
        hintText = "Confirm password"
    ) {
        confirmPassword = it
    }

    Button(modifier = Modifier.padding(0.dp, 6.dp),
        onClick = {
            onSignUpClicked(
                UserRequest(
                    givenName,
                    familyName,
                    profilePictureUrl,
                    phoneNumber,
                    email,
                    password
                )
            )
        }
    ) {
        Text(
            modifier = Modifier.padding(10.dp, 5.dp),
            style = TextStyle(fontSize = 14.sp),
            text = "Sign-up"
        )
    }
}


@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpContent(
        signUpUiState = SignUpUiState(),
        onSignUpClicked = {

        }
    )
}