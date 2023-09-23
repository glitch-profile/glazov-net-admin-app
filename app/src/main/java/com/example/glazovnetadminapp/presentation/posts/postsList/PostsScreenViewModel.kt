package com.example.glazovnetadminapp.presentation.posts.postsList

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsScreenViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase
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
            when (val result = postsUseCase.getAllPosts()) {
                is Resource.Success -> {
                    state = if (result.data !== null) {
                        state.copy(
                            posts = result.data,
                            isLoading = false
                        )
                    } else {
                        state.copy(
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