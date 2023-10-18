package com.example.glazovnetadminapp.presentation.posts.editPost

data class EditPostScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null
)
