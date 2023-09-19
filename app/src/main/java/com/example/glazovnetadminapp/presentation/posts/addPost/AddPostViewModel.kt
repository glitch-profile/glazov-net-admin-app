package com.example.glazovnetadminapp.presentation.posts.addPost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.posts.PostType
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase
): ViewModel() {
    fun submitPost(
        title: String,
        shortDescription: String,
        fullDescription: String,
        postType: Int,
        imageUrl: String,
        videoUrl: String
    ) {
        viewModelScope.launch {
            val status = postsUseCase.addPost(
                PostModel(
                    postId = "",
                    title = title,
                    creationDate = OffsetDateTime.now(ZoneId.systemDefault()),
                    shortDescription = shortDescription.ifBlank { null },
                    fullDescription = fullDescription,
                    postType = PostType.fromPostTypeCode(postType),
                    imageUrl = imageUrl.ifBlank { null },
                    videoUrl = videoUrl.ifBlank { null }
                )
            )
            Log.i("TAG", "submitPost: ${status.message}")
        }
    }
}