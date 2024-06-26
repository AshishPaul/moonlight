package com.zerogravity.moonlight.mobile.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconWithButton(
    modifier: Modifier = Modifier,
    @DrawableRes iconResource: Int,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    OutlinedButton(modifier = modifier, onClick = {
        onClick()
    }) {
        Icon(
            painterResource(id = iconResource),
            contentDescription = contentDescription,
            modifier = Modifier.padding(6.dp, 3.dp, 6.dp, 3.dp)
        )
    }
}