package com.example.glazovnetadminapp.presentation.posts.addPost

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.ImageModel
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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
        imageUrl: String
    ) {
        viewModelScope.launch {
            val currentTime = OffsetDateTime.now()
            val imageModel = if (imageUrl.isNotBlank()) {
                ImageModel(
                    imageUrl = imageUrl,
                    imageWidth = 1920f,
                    imageHeight = 1080f
                ) //TODO("Add dynamic ratio")
            } else null
            val status = postsUseCase.addPost(
                PostModel(
                    postId = "",
                    title = title,
                    creationDate = currentTime,
                    shortDescription = shortDescription.ifBlank { null },
                    fullDescription = fullDescription,
                    postType = PostType.fromPostTypeCode(postType),
                    image = imageModel
                )
            )
        }
    }
}