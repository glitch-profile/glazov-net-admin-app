package com.example.glazovnetadminapp.presentation.posts.postsList

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
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
import com.example.glazovnetadminapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
class PostsScreenViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(PostsScreenState())
    val state = _state.asStateFlow()

    var selectedPostToEdit: PostModel? = null //used to navigate to EditPostScreen
        private set
    var selectedPostToViewDetails: PostModel? = null //used to navigate to PostDetailsScreen
        private set

    init {
        getAllPosts()
    }

    fun setPostToEdit(postModel: PostModel?) {
        selectedPostToEdit = postModel
    }

    fun setPostToViewDetails(postModel: PostModel?) {
        selectedPostToViewDetails = postModel
    }

    fun getAllPosts() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            Log.i("TAG", "getAllPosts: now loading")
            when (val result = postsUseCase.getAllPosts()) {
                is Resource.Success -> {
                    _state.update {
                        if (result.data != null) {
                            it.copy(
                                posts = result.data.filterNotNull(),
                                isLoading = false
                            )
                        } else {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Log.i("TAG", "getAllPosts: loading failed!!")
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }

    fun deletePost(
        postIdToDelete: String
    ) {
        viewModelScope.launch {
            val result = postsUseCase.deletePostById(postIdToDelete)
            //TODO("add state messages")
            if (result.data == true) {
                val postIndex = state.value.posts.indexOfFirst { post ->
                    post.postId == postIdToDelete }
                if (postIndex == -1) {
                    return@launch
                } else {
                    val newPostsList = state.value.posts.toMutableList()
                    newPostsList.removeAt(postIndex)
                    _state.update {
                        it.copy(
                            posts = newPostsList
                        )
                    }
                }
            }
        }
    }

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
            val post = PostModel(
                postId = postId,
                title = postTitle,
                creationDate = postCreationDate,
                shortDescription = postShortDescription.ifBlank { null },
                fullDescription = postFullDescription,
                postType = PostType.fromPostTypeCode(postTypeCode),
                image = imageModel
            )
            val status = postsUseCase.updatePost(post)
            if (status.data == true) {
                val postIndex = state.value.posts.indexOfFirst {
                    it.postId == postId
                }
                if (postIndex == -1) {
                    return@launch
                } else {
                    val newPostsList = state.value.posts.toMutableList()
                    newPostsList.set(
                        index = postIndex,
                        element = post
                    )
                    _state.update {
                        it.copy(
                            posts = newPostsList
                        )
                    }
                }
            }
        }
    } //TODO("Add state messages")

    fun addNewPost(
        context: Context,
        title: String,
        shortDescription: String,
        fullDescription: String,
        postType: Int,
        imageUrl: String
    ) {
        viewModelScope.launch {
            val currentTime = OffsetDateTime.now()
            val imageModel = if (imageUrl.isNotBlank()) {
                val image = loadImage(context, imageUrl)
                image?.let {
                    ImageModel(
                        imageUrl = imageUrl,
                        imageWidth = it.intrinsicWidth,
                        imageHeight = it.intrinsicHeight
                    )
                }
            } else null
            val post = PostModel(
                postId = "",
                title = title,
                creationDate = currentTime,
                shortDescription = shortDescription.ifBlank { null },
                fullDescription = fullDescription,
                postType = PostType.fromPostTypeCode(postType),
                image = imageModel
            )
            val status = postsUseCase.addPost(post)
            if (status.data == null) {
                return@launch
            } else {
                val newPostsList = state.value.posts.toMutableList()
                newPostsList.add(
                    index = 0,
                    element = post
                )
                _state.update {
                    it.copy(
                        posts = newPostsList
                    )
                }
            }
        }
    } //TODO("Add state messages")

    private suspend fun loadImage(context: Context, url: String): Drawable? {
        val imageLoader = ImageLoader(context)
        val imageRequest = ImageRequest.Builder(context)
            .data(url)
            .diskCachePolicy(CachePolicy.READ_ONLY)
            .build()
        val imageResult = imageLoader.execute(imageRequest)
        return imageResult.drawable
    } //TODO("Add state messages")
}