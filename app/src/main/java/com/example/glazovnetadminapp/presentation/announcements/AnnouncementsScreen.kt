package com.example.glazovnetadminapp.presentation.announcements

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
import com.example.glazovnetadminapp.presentation.RequestErrorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(
    navController: NavController,
    viewModel: AnnouncementsViewModel
) {

    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_announcements_screen_name))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { navController.navigate("add_announcement") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create announcement"
                        )
                    }
                },
                scrollBehavior = scrollBehaviour
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            val state = viewModel.state.collectAsState().value
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
                if (state.message != null) {
                    state.message?.let {
                        RequestErrorScreen(
                            modifier = Modifier
                                .fillMaxSize(),
                            errorText = it,
                            onActionButtonClick = {
                                viewModel.getAllAnnouncements()
                            }
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = {
                        items(
                            items =  state.data,
                            key = { it.id }
                        ) {announcement ->
                            AnnouncementCard(
                                announcement = announcement,
                                onDeleteAnnouncement = {}
                            )
                        }
                    }
                )
            }
        }
    }
}