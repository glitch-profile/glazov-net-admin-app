package com.example.glazovnetadminapp.presentation.posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.posts.PostType
import com.example.glazovnetadminapp.domain.useCases.PostsEditUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val postsEditUseCase: PostsEditUseCase
): ViewModel() {
    fun submitPost(
        title: String,
        shortDescription: String,
        fullDescription: String,
        postType: String,
        imageUrl: String,
        videoUrl: String
    ) {
        viewModelScope.launch {
            val status = postsEditUseCase.addPost(
                PostModel(
                    postId = "",
                    title = title,
                    creationDate = OffsetDateTime.now(),
                    shortDescription = shortDescription.ifBlank { null },
                    fullDescription = fullDescription,
                    postType = PostType.fromPostTypeCode(1), //TODO("Add String Converter")
                    imageUrl = imageUrl.ifBlank { null },
                    videoUrl = videoUrl.ifBlank { null }
                )
            )
            Log.i("TAG", "submitPost: ${status.message}")
        }
    }
}