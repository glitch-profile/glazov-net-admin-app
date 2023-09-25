package com.example.glazovnetadminapp.presentation.posts.editPost

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import com.example.glazovnetadminapp.presentation.posts.addPost.AddPostScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(
    private val postUseCase: PostsUseCase
): ViewModel() {

    var state by mutableStateOf(AddPostScreenState())
        private set

    fun editPost(
        context: Context,
        isNeedToUpdateImage: Boolean,
        postId: String,
        postTitle: String,
        postCreationDate: OffsetDateTime,
        postFullDescription: String,
        postShortDescription: String? = null,
        postTypeCode: Int,
        postImageUrl: String,
        postImageWidth: Int? = null,
        postImageHeight: Int? = null
    ) {

    }
}