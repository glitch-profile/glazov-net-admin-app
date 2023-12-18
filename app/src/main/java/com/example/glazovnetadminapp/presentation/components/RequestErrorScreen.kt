package com.example.glazovnetadminapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.R

@Composable
fun RequestErrorScreen(
    modifier: Modifier = Modifier,
    errorText: String,
    onActionButtonClick: () -> Unit,
    errorTextColor: Color = MaterialTheme.colorScheme.error,
    errorTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
    actionButtonText: String = stringResource(id = R.string.app_buttom_try_again),
    actionButtonPadding: Dp = 8.dp
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = errorText,
            color = errorTextColor,
            style = errorTextStyle,
        )
        Button(
            modifier = Modifier
                .padding(actionButtonPadding)
                .fillMaxWidth(),
            onClick = { onActionButtonClick.invoke() }) {
            Text(
                text = actionButtonText
            )
        }
    }
}