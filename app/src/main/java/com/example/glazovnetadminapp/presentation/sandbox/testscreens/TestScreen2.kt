package com.example.glazovnetadminapp.presentation.sandbox.testscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.glazovnetadminapp.presentation.components.DesignedOutlinedTextField
import com.example.glazovnetadminapp.presentation.components.DesignedTextField

@Composable
fun TestScreen2(
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var editableText1 by remember{
            mutableStateOf("")
        }
        var editableText2 by remember{
            mutableStateOf("")
        }
        Text(text = "You are on a screen 2 with argument\n$value")
        DesignedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = "Title",
            text = editableText1,
            onTextEdit = {editableText1 = it}
        )
        DesignedOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = "Text",
            text = editableText2,
            onTextEdit = {editableText2 = it},
            minLines = 3
        )
    }
}