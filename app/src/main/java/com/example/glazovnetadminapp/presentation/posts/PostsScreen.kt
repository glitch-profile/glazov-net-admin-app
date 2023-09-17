package com.example.glazovnetadminapp.presentation.posts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.posts.PostType
import com.example.glazovnetadminapp.presentation.posts.addPost.AddPostScreen
import com.example.glazovnetadminapp.presentation.posts.postDetails.PostDetailScreen
import com.example.glazovnetadminapp.presentation.posts.postsList.PostsScrollableList
import java.time.OffsetDateTime
import java.time.ZoneId

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
        if (postsViewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Loading",
                    textAlign = TextAlign.Center
                )
            }
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
//            PostsScrollableList(
//                state = postsViewModel.state,
//                modifier = Modifier.fillMaxSize()
//            )
//            AddPostScreen()
            PostDetailScreen(
                post = PostModel(
                    postId = "6506ba8452052d5e6b16023b",
                    title = "Example post with some title",
                    creationDate = OffsetDateTime.now(ZoneId.systemDefault()),
                    fullDescription = "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas sit amet magna nec nulla tempor pellentesque non et nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Etiam id vehicula ipsum. Duis leo eros, pharetra nec justo ac, malesuada bibendum nunc. Vivamus blandit maximus pulvinar. Mauris maximus arcu sit amet dui dapibus, sollicitudin mattis dolor commodo. Nullam auctor consequat bibendum. Integer massa dolor, accumsan et molestie in, ultrices ac nunc. Curabitur vestibulum quis ante at tristique. Maecenas vel leo at sem posuere tempor in ac tellus. Duis sollicitudin non urna quis pellentesque. Nunc tristique, nulla at accumsan tincidunt, enim mauris rhoncus ex, quis mattis arcu magna sed nulla.",
                    postType = PostType.News,
                    imageUrl = "http://example.resorce.com/example-image-001.png"
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostsScreenPreview() {
    PostsScreen()
}