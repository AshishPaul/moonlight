package com.zerogravity.moonlight.mobile.android.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zerogravity.moonlight.mobile.android.presentation.ui.Green40
import com.zerogravity.moonlight.mobile.android.presentation.ui.Red40
import com.zerogravity.moonlight.mobile.common.domain.model.MessageBarState
import kotlinx.coroutines.delay

@Composable
fun MessageBar(messageBarState: MessageBarState) {
    var startAnimation by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(key1 = messageBarState) {
        message = messageBarState.message

        startAnimation = true
        delay(300)
        startAnimation = false
    }
    AnimatedVisibility(
        visible = startAnimation && messageBarState.message.isNotBlank(),
        enter = expandVertically(
            animationSpec = tween(300),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top
        )
    ) {
        MessageView(messageBarState)
    }
}

@Composable
fun MessageView(messageBarState: MessageBarState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (messageBarState.isError) Red40 else Green40)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (messageBarState.isError) Icons.Default.Warning else Icons.Default.Check,
            contentDescription = "Message Bar Icon",
            tint = Color.White
        )
        Divider(modifier = Modifier.width(12.dp), color = Color.Transparent)

        Text(
            text = messageBarState.message,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.button.fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun MessageBarSuccessPreview() {
    MessageView(messageBarState = MessageBarState("Successfully updated", isError = false))
}

@Preview
@Composable
fun MessageBarErrorPreview() {
    MessageView(messageBarState = MessageBarState("Successfully updated", isError = true))
}