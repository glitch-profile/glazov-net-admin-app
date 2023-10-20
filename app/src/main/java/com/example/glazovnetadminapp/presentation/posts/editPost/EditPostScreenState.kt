package com.example.glazovnetadminapp.presentation.posts.editPost

import com.example.glazovnetadminapp.domain.models.posts.PostModel

data class EditPostScreenState(
    val post: PostModel? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)
