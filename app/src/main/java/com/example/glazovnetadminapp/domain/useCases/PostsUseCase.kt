package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toPostModelDto
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.PostsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class PostsUseCase @Inject constructor(
    private val postsApiRepository: PostsApiRepository,
    private val localSettingsRepositoryImpl: LocalSettingsRepository
) {

    suspend fun getAllPosts(): Resource<List<PostModel>> {
        return postsApiRepository.getAllPosts()
    }

    suspend fun addPost(postModel: PostModel): Resource<PostModel?> {
        val postModelDto = postModel.toPostModelDto()
        return postsApiRepository.addPost(
            apiKey = localSettingsRepositoryImpl.getSavedApiKey(),
            postModel = postModelDto
        )
    }

    suspend fun getPostById(postId: String): Resource<PostModel?> {
        return postsApiRepository.getPostById(postId)
    }

    suspend fun deletePostById(postId: String): Resource<Boolean> {
        return postsApiRepository.deletePostById(
            localSettingsRepositoryImpl.getSavedApiKey(),
            postId = postId
        )
    }

    suspend fun updatePost(postModel: PostModel): Resource<Boolean> {
        val postModelDto = postModel.toPostModelDto()
        return postsApiRepository.editPost(
            localSettingsRepositoryImpl.getSavedApiKey(),
            postModelDto
        )
    }
}