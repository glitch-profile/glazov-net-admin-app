package com.example.glazovnetadminapp.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val nestedScroll = TopAppBarDefaults.pinnedScrollBehavior()

    val authState = viewModel.state.collectAsState()

    val loginToken = viewModel.loginToken.collectAsState()
    val userLoginState = viewModel.userLogin.collectAsState()
    val userPasswordState = viewModel.userPassword.collectAsState()
    val isRememberAuthData = viewModel.isRememberToken.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScroll.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_settings_screen_name)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(values)
        ) {
            Text(
                text = stringResource(id = R.string.settings_authorization_title),
                modifier = Modifier
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = userLoginState.value,
                onValueChange = {
                    viewModel.updateUserLoginString(it)
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.settings_authorization_login_text)
                    )
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                value = userPasswordState.value,
                onValueChange = {
                    viewModel.updateUserPasswordString(it)
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.settings_authorization_password_text)
                    )
                },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isRememberAuthData.value,
                    onCheckedChange = { viewModel.checkShouldRememberAuth() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Remember me"
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { viewModel.login(isAdmin = true) },
                    enabled = !authState.value.isLoading
                ) {
                    Text(
                        text = stringResource(id = R.string.settings_authorization_login_as_admin_button)
                    )
                }
                Button(
                    onClick = { viewModel.login(isAdmin = false) },
                    enabled = !authState.value.isLoading
                ) {
                    Text(
                        text = stringResource(id = R.string.settings_authorization_login_as_user_button)
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "Your auth token:"
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = loginToken.value.ifBlank { "Not authorized" }
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                text = authState.value.message ?: ""
            )
        }
    }
}