package com.example.glazovnetadminapp.presentation.sandbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.presentation.components.DesignedTextField

@Composable
fun SandboxScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        var inputText by remember {
            mutableStateOf("")
        }
        DesignedTextField(
            text = inputText,
            placeholder = "username",
            onTextEdit = {inputText = it},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        DesignedTextField(
            text = inputText,
            placeholder = "password",
            onTextEdit = {inputText = it}
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = inputText,
            onValueChange = {inputText = it},
            label = {
                Text(text = "password")
            }
        )
    }
}