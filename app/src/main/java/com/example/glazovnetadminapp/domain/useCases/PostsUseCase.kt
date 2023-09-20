package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toPostModelDto
import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

const val API_KEY = "J3gHkW9iLp7vQzXrE5NtFmAsCfYbDqUo"

class PostsUseCase @Inject constructor(
    private val postsApiRepositoryImpl: PostsApiRepositoryImpl
) {

    suspend fun getAllPosts(): Resource<List<PostModel?>> {
        return postsApiRepositoryImpl.getAllPosts()
    }

    suspend fun addPost(postModel: PostModel): Resource<Boolean> {
        val postModelDto = postModel.toPostModelDto()
        return postsApiRepositoryImpl.addPost(
            apiKey = API_KEY,
            postModel = postModelDto
        )
    }

    suspend fun getPostById(postId: String): Resource<PostModel?> {
        return postsApiRepositoryImpl.getPostById(postId)
    }

    suspend fun deletePostById(postId: String): Resource<Boolean> {
        return postsApiRepositoryImpl.deletePostById(API_KEY, postId = postId)
    }
}