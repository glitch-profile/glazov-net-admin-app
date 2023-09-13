package com.example.glazovnetadminapp.presentation.posts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PostsScreen(
    modifier: Modifier = Modifier
) {

    val postsViewModel: PostsScreenViewModel = viewModel()
    Log.i("TAG", "PostsScreen: Posts Screen Tick Here!")
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Api.Posts",
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .padding(8.dp)
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
        )
        if (postsViewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(16.dp)
                    .fillMaxSize(0.9f)
            )
        } else {
            postsViewModel.state.errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            PostsScrollableList(
                state = postsViewModel.state,
                modifier = Modifier.fillMaxSize(0.9f)
            )
        }
        Button(
            enabled = !postsViewModel.state.isLoading,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            onClick = { postsViewModel.getAllPosts() }) {
            Text(
                text = "Update Posts!"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostsScreenPreview() {
    PostsScreen()
}