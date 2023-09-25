package com.example.glazovnetadminapp.presentation.posts.addPost

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.glazovnetadminapp.domain.models.ImageModel
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    var state by mutableStateOf(AddPostScreenState())
        private set

    fun submitPost(
        context: Context,
        title: String,
        shortDescription: String,
        fullDescription: String,
        postType: Int,
        imageUrl: String
    ) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isError = false,
                message = "getting info..."
            )
            val currentTime = OffsetDateTime.now()
            val imageModel = if (imageUrl.isNotBlank()) {
                state = state.copy(
                    message = "getting image..."
                )
                val image = loadImage(context, imageUrl)
                image?.let {
                    ImageModel(
                        imageUrl = imageUrl,
                        imageWidth = it.intrinsicWidth,
                        imageHeight = it.intrinsicHeight
                    )
                }
            } else null
            state = state.copy(
                message = "uploading..."
            )
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
            state = state.copy(
                isLoading = false,
                isError = (status.data?.not()) ?: true,
                message = status.message
            )
        }
    }

    private suspend fun loadImage(context: Context, url: String): Drawable? {
        val imageLoader = ImageLoader(context)
        val imageRequest = ImageRequest.Builder(context)
            .data(url)
            .diskCachePolicy(CachePolicy.READ_ONLY)
            .build()
        val imageResult = imageLoader.execute(imageRequest)
        return imageResult.drawable
    }
}