package com.zerogravity.moonlight.mobile.android.presentation.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.zerogravity.moonlight.mobile.android.R

@Composable
fun PasswordInputField(
    localFocusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    password: String,
    hintText: String = "Enter your password",
    onValueChange: (String) -> Unit
) {

    var showPassword by remember { mutableStateOf(false) }

    val iconResource = if (showPassword) {
        R.drawable.ic_visibility_on
    } else {
        R.drawable.ic_visibility_off
    }

    InputField(
        focusManager = localFocusManager,
        keyboardController = keyboardController,
        text = password,
        hintText = hintText,
        leadingIcon = Icons.Outlined.AccountCircle,
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        onValueChange = onValueChange,
        trailingIcon = iconResource,
        onTrailingButtonClick = {
            showPassword = !showPassword
        }
    )
}