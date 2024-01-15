package com.example.glazovnetadminapp.presentation.posts.postsList

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Size
import androidx.core.graphics.decodeBitmap
import androidx.core.graphics.scale
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.ImageModel
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import com.example.glazovnetadminapp.domain.useCases.UtilsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.presentation.ScreenState
import com.example.glazovnetadminapp.presentation.posts.editPost.EditPostScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.use
import java.io.File
import java.io.FileOutputStream
import java.time.OffsetDateTime
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class PostsScreenViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase,
    private val utilsUseCase: UtilsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ScreenState<PostModel>())
    val state = _state.asStateFlow()

    private val _editPostState = MutableStateFlow(EditPostScreenState())
    val editPostState = _editPostState.asStateFlow()

    private val _openedPostState = MutableStateFlow<PostModel?>(null)
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
        postModel: PostModel
    ) {
        viewModelScope.launch{
            val result = postsUseCase.deletePostById(postModel.postId)
            if (result.data == true) {
                val postList = state.value.data.toMutableList()
                if (postList.remove(postModel)) {
                    _state.update {
                        it.copy(data = postList)
                    }
                }
                if (openedPostModel.value?.postId == postModel.postId) {
                    setPostToViewDetails(null)
                }
            } else {
                _state.update {
                    it.copy(message = result.message)
                }
            }
        }
    }

    fun editPost(
        context: Context,
        postTitle: String,
        postText: String,
        imageUri: String,
    ) {
        val currentPost = editPostState.value.post!!
        viewModelScope.launch {
            _editPostState.update {
                it.copy(
                    isLoading = true,
                    message = "getting post data..."
                )
            }
            val imageModel = if (imageUri !== (currentPost.image?.imageUrl ?: "")) {
                if (imageUri.isNotBlank()) uploadImageToServer(context, imageUri)
                else null
            } else {
                if (imageUri.isNotBlank()) {
                    ImageModel(
                        imageUrl = imageUri,
                        imageWidth = currentPost.image!!.imageWidth,
                        imageHeight = currentPost.image!!.imageHeight
                    )
                } else null
            }
            val post = PostModel(
                postId = currentPost.postId,
                title = postTitle,
                creationDate = currentPost.creationDate,
                text = postText,
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
                    it.postId == currentPost.postId
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
        text: String,
        imageUri: String
    ) {
        viewModelScope.launch {
            _editPostState.update {
                it.copy(
                    isLoading = true,
                    message = "getting post data..."
                )
            }
            val currentTime = OffsetDateTime.now()
            val imageModel = if (imageUri.isNotBlank()) uploadImageToServer(context, imageUri)
            else null
            val post = PostModel(
                postId = "",
                title = title,
                creationDate = currentTime,
                text = text,
                image = imageModel
            )
            _editPostState.update {
                it.copy(
                    message = "uploading post..."
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

    private suspend fun uploadImageToServer(context: Context, imageUriString: String): ImageModel? {
        _editPostState.update {
            it.copy(
                message = "uploading image..."
            )
        }
        val fileBites = context.contentResolver.openInputStream(Uri.parse(imageUriString))?.use {
            it.readBytes()
        }
        val fileName = getImageName(imageUriString)
        val file = File(context.cacheDir, fileName)
        withContext(Dispatchers.IO) {
            FileOutputStream(file).use {
                it.write(fileBites)
            }
        }
        val image = getCompressedImage(file)
        val imageModel = when (val uploadResult = utilsUseCase.uploadFile(file)) {
            is Resource.Success -> {
                val imageUrl = uploadResult.data!!.singleOrNull()
                if (imageUrl != null) {
                    ImageModel(
                        imageUrl = imageUrl,
                        imageWidth = image.width,
                        imageHeight = image.height
                    )
                } else null
            }
            is Resource.Error -> null
        }
        image.recycle()
        file.delete()
        return imageModel
    }

    private fun getImageName(filePath: String): String {
        val fileNameTrimCount = if (filePath.contains('%')) filePath.reversed().indexOf("%")
        else filePath.reversed().indexOf("/")
        var fileName = filePath.takeLast(fileNameTrimCount)
        val fileExtension = File(fileName).extension
        if (fileExtension.isBlank()) fileName += ".jpg"
        return fileName
    }

    private fun getCompressedImage(
        file: File,
        maxDimensionSize: Float = 1920f,
    ): Bitmap {
        var image = ImageDecoder.createSource(file).decodeBitmap { _, _ ->  }
        val maxDimension = maxOf(image.height, image.width)
        if (maxDimension > maxDimensionSize) {
            val scaleFactor = maxDimensionSize / maxDimension
            val newImageWidth = (image.width * scaleFactor).roundToInt()
            val newImageHeight = (image.height * scaleFactor).roundToInt()
            image = image.scale(newImageWidth, newImageHeight)
        }
        file.outputStream().use {
            image.compress(Bitmap.CompressFormat.JPEG, 80, it)
        } //writing new image to file
        return image
    }
}