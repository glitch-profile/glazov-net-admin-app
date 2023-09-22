package com.example.glazovnetadminapp.presentation.posts

import com.example.glazovnetadminapp.domain.models.posts.PostModel

data class PostsScreenState(
    val posts: List<PostModel?> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)