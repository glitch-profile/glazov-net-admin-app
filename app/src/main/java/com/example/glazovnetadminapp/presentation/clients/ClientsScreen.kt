package com.example.glazovnetadminapp.presentation.clients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.glazovnetadminapp.R
import com.example.glazovnetadminapp.presentation.components.RequestErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(
    navController: NavController,
    viewModel: ClientsViewModel
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val state = viewModel.clientsScreenState.collectAsState().value
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_clients_screen_name)
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
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("add_client")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create client"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
                .padding(horizontal = 16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.app_text_loading),
                    textAlign = TextAlign.Center
                )
            } else {
                state.message?.let {
                    RequestErrorScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        errorText = it,
                        onActionButtonClick = {
                            viewModel.getAllClients()
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    items(state.data) {
                        ClientCard(
                            client = it,
                        )
                    }
                }
            )
        }
    }
}