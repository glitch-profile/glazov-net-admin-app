package com.example.glazovnetadminapp.presentation.posts.postsList

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
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
import com.example.glazovnetadminapp.presentation.ScreenState
import com.example.glazovnetadminapp.presentation.posts.editPost.EditPostScreenState
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

    private var _state = MutableStateFlow(ScreenState<PostModel>())
    val state = _state.asStateFlow()

    private var _editPostState = MutableStateFlow(EditPostScreenState())
    val editPostState = _editPostState.asStateFlow()

    private var _openedPostState = MutableStateFlow<PostModel?>(null)
    val openedPostModel = _openedPostState.asStateFlow()

    init {
        getAllPosts()
    }

    fun setPostToEdit(postModel: PostModel?) {
        _editPostState.update {
            EditPostScreenState(
                post = postModel
            )
        }
    }

    fun setPostToViewDetails(postModel: PostModel?) {
        _openedPostState.update {
            postModel
        }
    }

    fun getAllPosts() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            Log.i("TAG", "getAllPosts: now loading")
            when (val result = postsUseCase.getAllPosts()) {
                is Resource.Success -> {
                    _state.update {
                        if (result.data != null) {
                            it.copy(
                                data = result.data,
                                isLoading = false
                            )
                        } else {
                            it.copy(
                                isLoading = false,
                                message = result.message
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = result.message
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
                val postIndex = state.value.data.indexOfFirst { post ->
                    post.postId == postIdToDelete }
                if (postIndex == -1) {
                    return@launch
                } else {
                    val newPostsList = state.value.data.toMutableList()
                    newPostsList.removeAt(postIndex)
                    _state.update {
                        it.copy(
                            data = newPostsList
                        )
                    }
                    setPostToViewDetails(null)
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
            _editPostState.update {
                it.copy(
                    isLoading = true,
                    message = "getting post data..."
                )
            }
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
            _editPostState.update {
                it.copy(
                    message = "updating post..."
                )
            }
            val status = postsUseCase.updatePost(post)
            if (status.data == true) {
                val postIndex = state.value.data.indexOfFirst {
                    it.postId == postId
                }
                if (postIndex == -1) {
                    _editPostState.update {
                        it.copy(
                            isLoading = false,
                            message = "error while updating post locally"
                        )
                    }
                    return@launch
                } else {
                    val newPostsList = state.value.data.toMutableList()
                    newPostsList.set(
                        index = postIndex,
                        element = post
                    )
                    _state.update {
                        it.copy(
                            data = newPostsList
                        )
                    }
                    setPostToViewDetails(post)
                }
            }
            _editPostState.update {
                it.copy(
                    isLoading = false,
                    message = if (status.data == true) "Complete!" else status.message
                )
            }
        }
    }

    fun addNewPost(
        context: Context,
        title: String,
        shortDescription: String,
        fullDescription: String,
        postType: Int,
        imageUrl: String
    ) {
        viewModelScope.launch {
            _editPostState.update {
                it.copy(
                    isLoading = true,
                    message = "getting post data..."
                )
            }
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
            _editPostState.update {
                it.copy(
                    message = "saving post..."
                )
            }
            val status = postsUseCase.addPost(post)
            if (status.data == null) {
                _editPostState.update {
                    it.copy(
                        isLoading = false,
                        message = status.message
                    )
                }
                return@launch
            } else {
                val newPostsList = state.value.data.toMutableList()
                newPostsList.add(
                    index = 0,
                    element = status.data
                )
                _state.update {
                    it.copy(
                        data = newPostsList
                    )
                }
            }
            _editPostState.update {
                it.copy(
                    isLoading = false,
                    message = "completed"
                )
            }
        }
    }

    private suspend fun loadImage(context: Context, url: String): Drawable? {
        _editPostState.update {
            it.copy(
                message = "calculating image size..."
            )
        }
        val imageLoader = ImageLoader(context)
        val imageRequest = ImageRequest.Builder(context)
            .data(url)
            .diskCachePolicy(CachePolicy.READ_ONLY)
            .build()
        val imageResult = imageLoader.execute(imageRequest)
        return imageResult.drawable
    }
}