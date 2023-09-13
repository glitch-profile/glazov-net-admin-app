package com.example.glazovnetadminapp.presentation.posts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PostsScrollableList(
    state: PostsScreenState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        content = {
        items(state.posts) {
            PostCard(
                postModel = it
            )
        }
    })
}