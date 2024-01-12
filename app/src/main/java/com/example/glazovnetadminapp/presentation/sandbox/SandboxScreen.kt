package com.example.glazovnetadminapp.presentation.sandbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
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
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.glazovnetadminapp.presentation.components.DesignedTextField
import com.example.glazovnetadminapp.presentation.sandbox.testscreens.TestScreen1
import com.example.glazovnetadminapp.presentation.sandbox.testscreens.TestScreen2

@Composable
fun SandboxScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val innerNavController = rememberNavController()
        var value by remember {
            mutableStateOf("some value")
        }
        NavHost(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            navController = innerNavController,
            startDestination = "screen_1"
        ) {
            composable("screen_1") {
                TestScreen1()
            }
            composable(
                route = "screen_2/{value}",
                arguments = listOf(
                    navArgument("value") {
                        type = NavType.StringType
                    }
                )
            ) {
                val argument = it.arguments?.getString("value") ?: "default value"
                TestScreen2(argument)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    innerNavController.navigate("screen_1") {
                        popUpTo("screen_1") {inclusive = true}
                    }
                }
            ) {
                Text(text = "To screen 1")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    innerNavController.navigate(route = "screen_2/$value") {
                        popUpTo("screen_2") {inclusive = true}
                    }
                }
            ) {
                Text(text = "To screen 2")
            }
        }
    }
}