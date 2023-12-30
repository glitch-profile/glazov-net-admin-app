package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.postsDto.PostModelDto

interface PostsApiRepository {

    suspend fun getAllPosts(
        token: String
    ): Resource<List<PostModel>>

    suspend fun getPostsList (
        limit: Int? = null,
        startIndex: Int? = null,
        token: String
    ): Resource<List<PostModel>>

    suspend fun getPostById (
        postId: String,
        token: String
    ): Resource<PostModel?>

    suspend fun addPost(
        postModel: PostModelDto,
        token: String
    ): Resource<PostModel?>

    suspend fun editPost(
        postModel: PostModelDto,
        token: String
    ): Resource<Boolean>

    suspend fun deletePostById(
        postId: String,
        token: String
    ): Resource<Boolean>
}