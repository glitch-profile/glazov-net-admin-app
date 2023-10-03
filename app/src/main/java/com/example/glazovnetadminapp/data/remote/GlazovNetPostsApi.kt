package com.example.glazovnetadminapp.data.remote

import com.example.glazovnetadminapp.entity.postsDto.PostModelDto
import com.example.glazovnetadminapp.entity.postsDto.PostsResponceDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

private const val PATH = "api/posts"

interface GlazovNetPostsApi {

    @GET("$PATH/getall")
    suspend fun getAllPosts(): PostsResponceDto

    @GET("$PATH/getposts")
    suspend fun getPostsList(
        @Query("limit") limit: Int? = null,
        @Query("start_index") startIndex: Int? = null
    ): PostsResponceDto

    @GET("$PATH/get")
    suspend fun getPostById(
        @Query("post_id") postId: String
    ): PostsResponceDto

    @POST("$PATH/add")
    suspend fun addPost(
        @Body postModel: PostModelDto,
        @Query("api_key") apiKey: String
    ): PostsResponceDto

    @PUT("$PATH/edit")
    suspend fun editPost(
        @Body postModel: PostModelDto,
        @Query("api_key") apiKey: String
    ): PostsResponceDto

    @DELETE("$PATH/delete")
    suspend fun deletePostById(
        @Query("api_key") apiKey: String,
        @Query("post_id") postId: String
    ): PostsResponceDto
}