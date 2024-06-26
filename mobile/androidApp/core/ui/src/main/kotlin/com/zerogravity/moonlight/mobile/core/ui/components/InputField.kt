package com.zerogravity.moonlight.mobile.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    text: String,
    hintText: String,
    leadingIcon: ImageVector,
    @DrawableRes trailingIcon: Int? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions,
    onTrailingButtonClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .padding(0.dp, 8.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        value = text,
        onValueChange = onValueChange,
        label = { Text(hintText) },
        leadingIcon = {
            Icon(
                leadingIcon,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(onClick = onTrailingButtonClick) {
                    Icon(
                        painterResource(id = trailingIcon),
                        contentDescription = "Visibility",
                    )
                }
            }
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) },
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ),
    )
}