package com.example.glazovnetadminapp.presentation.posts

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.repository.PostsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsScreenViewModel @Inject constructor(
    private val postsApiRepository: PostsApiRepository
): ViewModel() {

    var state by mutableStateOf(PostsScreenState())
        private set

    init {
        getAllPosts()
    }

    fun getAllPosts() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )
            Log.i("TAG", "getAllPosts: now loading")
            when (val result = postsApiRepository.getAllPosts()) {
                is Resource.Success -> {
                    if (result.data !== null) {
                        state = state.copy(
                            posts = result.data,
                            isLoading = false
                        )
                    } else {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
                is Resource.Error -> {
                    Log.i("TAG", "getAllPosts: loading failed!!")
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }
}