package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toPostModelDto
import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

private const val API_KEY = "test_api_key_123"

class PostsUseCase @Inject constructor(
    private val postsApiRepository: PostsApiRepositoryImpl
) {

    suspend fun getAllPosts(): Resource<List<PostModel?>> {
        return postsApiRepository.getAllPosts()
    }

    suspend fun addPost(postModel: PostModel): Resource<Boolean> {
        val postModelDto = postModel.toPostModelDto()
        return postsApiRepository.addPost(
            apiKey = API_KEY,
            postModel = postModelDto
        )
    }

    suspend fun getPostById(postId: String): Resource<PostModel?> {
        return postsApiRepository.getPostById(postId)
    }

    suspend fun deletePostById(postId: String): Resource<Boolean> {
        return postsApiRepository.deletePostById(API_KEY, postId = postId)
    }

    suspend fun updatePost(postModel: PostModel): Resource<Boolean> {
        val postModelDto = postModel.toPostModelDto()
        return postsApiRepository.editPost(API_KEY, postModelDto)
    }
}