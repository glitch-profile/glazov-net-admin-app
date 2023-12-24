package com.example.glazovnetadminapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.domain.util.convertDaysOffsetToString
import java.time.OffsetDateTime

@Composable
fun ChatMessageBubble(
    modifier: Modifier = Modifier,
    senderName: String,
    text: String,
    timestamp: OffsetDateTime,
    isOwnMessage: Boolean,
    maxBubbleWidth: Dp = 250.dp,
    //backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    Row(
        modifier = modifier,
        horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start
    ) {
        val backgroundColor = if (isOwnMessage) MaterialTheme.colorScheme.secondaryContainer
        else MaterialTheme.colorScheme.primaryContainer
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(backgroundColor)
                .padding(8.dp)
                .widthIn(max = maxBubbleWidth)
        ) {
            Text(
                text = if (!isOwnMessage) senderName
                else stringResource(id = R.string.app_chat_senderName_self),
                style = MaterialTheme.typography.titleSmall,
                color = textColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = timestamp.convertDaysOffsetToString(
                    daysPattern = "dd MMMM"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = textColor
            )
        }
    }
}