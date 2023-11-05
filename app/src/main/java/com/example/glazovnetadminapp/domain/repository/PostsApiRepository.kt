package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.postsDto.PostModelDto

interface PostsApiRepository {

    suspend fun getAllPosts(): Resource<List<PostModel>>

    suspend fun getPostsList (
        limit: Int? = null,
        startIndex: Int? = null
    ): Resource<List<PostModel>>

    suspend fun getPostById (
        postId: String
    ): Resource<PostModel?>

    suspend fun addPost(
        apiKey: String,
        postModel: PostModelDto
    ): Resource<PostModel?>

    suspend fun editPost(
        apiKey: String,
        postModel: PostModelDto
    ): Resource<Boolean>

    suspend fun deletePostById(
        apiKey: String,
        postId: String
    ): Resource<Boolean>
}