package com.example.glazovnetadminapp.presentation.sandbox.testscreens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TestScreen2(
    value: String
) {
    Text(text = "You are on a screen 2 with argument\n$value")
}