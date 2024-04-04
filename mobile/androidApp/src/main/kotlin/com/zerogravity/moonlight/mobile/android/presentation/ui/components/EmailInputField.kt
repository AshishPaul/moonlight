package com.zerogravity.moonlight.mobile.android.presentation.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.zerogravity.moonlight.mobile.android.presentation.ui.components.InputField

@Composable
fun EmailInputField(
    localFocusManager: FocusManager,
    email: String,
    onValueChange: (String) -> Unit
) {
    InputField(
        focusManager = localFocusManager,
        text = email,
        hintText = "Email",
        leadingIcon = Icons.Outlined.Email,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        onValueChange = onValueChange
    )
}