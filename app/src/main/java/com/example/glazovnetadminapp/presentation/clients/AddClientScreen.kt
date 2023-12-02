package com.example.glazovnetadminapp.presentation.clients

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.presentation.components.TextFieldWithCompleteSuggestions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClientScreen(
    navController: NavController,
    viewModel: ClientsViewModel
) {
    val state = viewModel.addClientScreenState.collectAsState().value
    val citiesInteractionSource = remember { MutableInteractionSource() }
    val streetsInteractionSource = remember { MutableInteractionSource() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create client"
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
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { values ->
        var accountNumber by remember { mutableStateOf("") }
        var login by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var middleName by remember { mutableStateOf("") }
        var cityName by remember { mutableStateOf("") }
        var streetName by remember { mutableStateOf("") }
        var houseNumber by remember { mutableStateOf("") }
        var roomNumber by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(values)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val citiesList = viewModel.citiesList.collectAsState()
            val streetsList = viewModel.streetsList.collectAsState()
            OutlinedTextField(
                value = accountNumber,
                onValueChange = { accountNumber = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Account number"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            Divider(Modifier.fillMaxWidth())
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "last name"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "First name"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = middleName,
                onValueChange = { middleName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Middle name"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Divider(Modifier.fillMaxWidth())
            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Login"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = {
                    Text(
                        text = "Password"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Divider(Modifier.fillMaxWidth())
            TextFieldWithCompleteSuggestions(
                text = cityName,
                onTextChanges = {
                    cityName = it
                    viewModel.updateCitiesSearch(it)
                },
                suggestions = citiesList.value,
                label = "City",
                interactionSource = citiesInteractionSource,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            TextFieldWithCompleteSuggestions(
                text = streetName,
                onTextChanges = {
                    streetName = it
                    viewModel.updateStreetsSearch(it)
                },
                suggestions = streetsList.value,
                label = "Street",
                interactionSource = streetsInteractionSource,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = houseNumber,
                    onValueChange = { houseNumber = it },
                    modifier = Modifier
                        .weight(1f),
                    label = {
                        Text(
                            text = "House number"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = roomNumber,
                    onValueChange = { roomNumber = it },
                    modifier = Modifier
                        .weight(1f),
                    label = {
                        Text(
                            text = "Room number"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        fun clearInputData() {
                            accountNumber = ""
                            login = ""
                            password = ""
                            lastName = ""
                            firstName = ""
                            middleName = ""
                            cityName = ""
                            streetName = ""
                            houseNumber = ""
                            roomNumber = ""
                        }

                        clearInputData()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.app_button_reset_data)
                    )
                }
                Button(
                    onClick = {
                        viewModel.createClient(
                            accountNumber = accountNumber,
                            login = login,
                            password = password,
                            lastName = lastName,
                            firstName = firstName,
                            middleName = middleName,
                            cityName = cityName,
                            streetName = streetName,
                            houseNumber = houseNumber,
                            roomNumber = roomNumber,
                            callback = { status, message ->
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        if (status) "Completed"
                                        else message
                                    )
                                }
                            }
                        )
                    },
                    enabled = (
                            !state.isLoading && accountNumber.isNotBlank() && login.isNotBlank()
                                    && password.isNotBlank() && lastName.isNotBlank()
                                    && firstName.isNotBlank() && cityName.isNotBlank()
                                    && streetName.isNotBlank() && houseNumber.isNotBlank()
                                    && roomNumber.isNotBlank()
                            )
                ) {
                    Text(text = stringResource(id = R.string.add_post_screen_confirm_button))
                }
            }
        }
    }
}