package com.example.glazovnetadminapp.presentation.posts.addPost

data class AddPostScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null
)
