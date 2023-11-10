package com.example.glazovnetadminapp.presentation

data class ScreenState<T>(
    val data: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null
)
