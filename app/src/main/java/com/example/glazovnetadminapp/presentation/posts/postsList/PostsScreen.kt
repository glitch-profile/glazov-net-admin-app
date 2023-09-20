package com.example.glazovnetadminapp.presentation.posts.postsList

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.glazovnetadminapp.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PostsScreen(
    navigator: DestinationsNavigator,
    postsViewModel: PostsScreenViewModel = hiltViewModel()
) {

    Log.i("TAG", "PostsScreen: Posts Screen Tick Here!")
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_posts_screen_name))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                          IconButton(
                              onClick = { postsViewModel.getAllPosts() }
                          ) {
                              Icon(
                                  imageVector = Icons.Default.Refresh,
                                  contentDescription = "Refresh posts"
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
            if (postsViewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(16.dp)
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Loading",
                    textAlign = TextAlign.Center
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
                    Button(
                        enabled = !postsViewModel.state.isLoading,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        onClick = { postsViewModel.getAllPosts() }) {
                        Text(
                            text = "Try again!"
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    content = {
                        items(postsViewModel.state.posts) {
                            PostCard(
                                postModel = it,
                                navigator = navigator
                            )
                        }
                    }
                )
//            AddPostScreen()
//            PostDetailScreen(
//                post = PostModel(
//                    postId = "6506ba8452052d5e6b16023b",
//                    title = "Example post with some title",
//                    creationDate = OffsetDateTime.now(ZoneId.systemDefault()),
//                    fullDescription = "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas sit amet magna nec nulla tempor pellentesque non et nulla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Etiam id vehicula ipsum. Duis leo eros, pharetra nec justo ac, malesuada bibendum nunc. Vivamus blandit maximus pulvinar. Mauris maximus arcu sit amet dui dapibus, sollicitudin mattis dolor commodo. Nullam auctor consequat bibendum. Integer massa dolor, accumsan et molestie in, ultrices ac nunc. Curabitur vestibulum quis ante at tristique. Maecenas vel leo at sem posuere tempor in ac tellus. Duis sollicitudin non urna quis pellentesque. Nunc tristique, nulla at accumsan tincidunt, enim mauris rhoncus ex, quis mattis arcu magna sed nulla.",
//                    postType = PostType.News,
//                    imageUrl = "http://example.resorce.com/example-image-001.png"
//                )
//            )
            }
        }
    }

}
