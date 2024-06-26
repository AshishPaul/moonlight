package com.zerogravity.moonlight.mobile.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zerogravity.moonlight.mobile.core.ui.Blue30
import com.zerogravity.moonlight.mobile.core.ui.Shapes
import com.zerogravity.moonlight.mobile.core.ui.R

@Composable
fun LoadingAuthButton(
    modifier: Modifier = Modifier,
    loadingState: Boolean = false,
    primaryText: String = "Sign in with Google",
    loadingText: String = "Please wait...",
    icon: Int = R.drawable.ic_google_filled_color,
    shape: Shape = Shapes.medium,
    borderColor: Color = Color.LightGray,
    borderStrokeWidth: Dp = 1.dp,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = Blue30,
    onClick: () -> Unit
) {
    var buttonText by remember { mutableStateOf(primaryText) }

    LaunchedEffect(key1 = loadingState) {
        buttonText = if (loadingState) loadingText else primaryText
    }

    Surface(
        modifier = modifier
            .clickable(enabled = !loadingState) {
                onClick()
            },
        shape = shape,
        border = BorderStroke(width = borderStrokeWidth, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = primaryText,
//                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = buttonText, fontSize = MaterialTheme.typography.button.fontSize)
            if (loadingState) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }

        }
    }
}

@Preview
@Composable
fun LoadingAuthButtonPreview() {
    LoadingAuthButton(loadingState = false) {}
}