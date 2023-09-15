package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toPostModelDto
import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.util.Resource
import java.net.ConnectException
import javax.inject.Inject

const val API_KEY = "J3gHkW9iLp7vQzXrE5NtFmAsCfYbDqUo"

class PostsEditUseCase @Inject constructor(
    private val postsApiRepositoryImpl: PostsApiRepositoryImpl
) {

    suspend fun addPost(postModel: PostModel): Resource<Boolean> {
        val postModelDto = postModel.toPostModelDto()
        println(postModelDto)
        return postsApiRepositoryImpl.addPost(
                apiKey = API_KEY,
                postModel = postModelDto
            )
    }

}