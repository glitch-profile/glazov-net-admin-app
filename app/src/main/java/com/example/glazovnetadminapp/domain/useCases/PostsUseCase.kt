package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toPostModelDto
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.PostsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class PostsUseCase @Inject constructor(
    private val postsApiRepository: PostsApiRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {

    suspend fun getAllPosts(): Resource<List<PostModel>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return postsApiRepository.getAllPosts(token)
    }

    suspend fun addPost(postModel: PostModel): Resource<PostModel?> {
        val postModelDto = postModel.toPostModelDto()
        val token = localSettingsRepository.getLoginToken() ?: ""
        return postsApiRepository.addPost(
            token = token,
            postModel = postModelDto
        )
    }

    suspend fun getPostById(postId: String): Resource<PostModel?> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return postsApiRepository.getPostById(postId, token)
    }

    suspend fun deletePostById(postId: String): Resource<Boolean> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return postsApiRepository.deletePostById(
            token = token,
            postId = postId
        )
    }

    suspend fun updatePost(postModel: PostModel): Resource<Boolean> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        val postModelDto = postModel.toPostModelDto()
        return postsApiRepository.editPost(
            token = token,
            postModel = postModelDto
        )
    }
}