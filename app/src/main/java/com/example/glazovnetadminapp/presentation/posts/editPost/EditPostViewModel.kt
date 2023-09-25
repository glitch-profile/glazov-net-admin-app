package com.example.glazovnetadminapp.presentation.posts.editPost

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.glazovnetadminapp.domain.models.ImageModel
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import com.example.glazovnetadminapp.presentation.posts.addPost.AddPostScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase
) : ViewModel() {

    var state by mutableStateOf(AddPostScreenState())
        private set

    fun editPost(
        context: Context,
        isNeedToUpdateImage: Boolean,
        postId: String,
        postTitle: String,
        postCreationDate: OffsetDateTime,
        postFullDescription: String,
        postShortDescription: String,
        postTypeCode: Int,
        postImageUrl: String,
        postImageWidth: Int? = null,
        postImageHeight: Int? = null
    ) {

        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                message = "getting info...",
                isError = false
            )
            val imageModel = if (isNeedToUpdateImage) {
                if (postImageUrl.isNotBlank()) {
                    val image = loadImage(context, postImageUrl)
                    image?.let {
                        ImageModel(
                            postImageUrl,
                            it.intrinsicWidth,
                            it.intrinsicHeight
                        )
                    }
                } else null
            } else {
                if (postImageUrl.isNotBlank()) {
                    ImageModel(
                        imageUrl = postImageUrl,
                        imageWidth = postImageWidth!!,
                        imageHeight = postImageHeight!!
                    )
                } else null
            }
            state = state.copy(
                message = "uploading..."
            )
            val status = postsUseCase.updatePost(
                PostModel(
                    postId = postId,
                    title = postTitle,
                    creationDate = postCreationDate,
                    shortDescription = postShortDescription.ifBlank { null },
                    fullDescription = postFullDescription,
                    postType = PostType.fromPostTypeCode(postTypeCode),
                    image = imageModel
                )
            )
            state = state.copy(
                isLoading = false,
                message = status.message,
                isError = (status.data?.not()) ?: false
            )
        }
    }

    private suspend fun loadImage(context: Context, url: String): Drawable? {
        state = state.copy(
            message = "getting image..."
        )
        val imageLoader = ImageLoader(context)
        val imageRequest = ImageRequest.Builder(context)
            .data(url)
            .diskCachePolicy(CachePolicy.READ_ONLY)
            .build()
        val imageResult = imageLoader.execute(imageRequest)
        return imageResult.drawable
    }
}