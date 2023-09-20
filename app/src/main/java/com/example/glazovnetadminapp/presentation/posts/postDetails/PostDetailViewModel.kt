package com.example.glazovnetadminapp.presentation.posts.postDetails

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.presentation.posts.PostsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase
): ViewModel() {

    var state by mutableStateOf(PostsScreenState())
        private set
    var postId: String? = null

    fun getPostById(newPostId: String) {
        viewModelScope.launch {
            if (newPostId == postId) {
                return@launch
            }
            postId = newPostId
            state = state.copy(
                isLoading = true,
                errorMessage = null
            )
            val result = postsUseCase.getPostById(postId ?: newPostId)
            when (result) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Resource.Success -> {
                    state = if (result.data == null) {
                        state.copy(
                            isLoading = false,
                            errorMessage = "No data"
                        )
                    } else {
                        state.copy(
                            isLoading = false,
                            posts = listOf(result.data)
                        )
                    }
                }
            }
        }
    }

    fun deletePost(postIdToDelete: String) {
        viewModelScope.launch {
            postsUseCase.deletePostById(postIdToDelete)
            state = state.copy(
                errorMessage = "This post has been deleted..."
            )
        }
    }
}