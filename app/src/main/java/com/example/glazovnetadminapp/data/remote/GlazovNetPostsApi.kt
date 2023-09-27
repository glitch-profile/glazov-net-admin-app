package com.example.glazovnetadminapp.data.remote

import com.example.glazovnetadminapp.entity.postsDto.PostModelDto
import com.example.glazovnetadminapp.entity.postsDto.PostsResponceDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface GlazovNetPostsApi {

    @GET("posts/getall")
    suspend fun getAllPosts(): PostsResponceDto

    @GET("posts/getposts")
    suspend fun getPostsList(
        @Query("limit") limit: Int? = null,
        @Query("start_index") startIndex: Int? = null
    ): PostsResponceDto

    @GET("posts/get")
    suspend fun getPostById(
        @Query("post_id") postId: String
    ): PostsResponceDto

    @POST("posts/add")
    suspend fun addPost(
        @Body postModel: PostModelDto,
        @Query("api_key") apiKey: String
    ): PostsResponceDto

    @PUT("posts/edit")
    suspend fun editPost(
        @Body postModel: PostModelDto,
        @Query("api_key") apiKey: String
    ): PostsResponceDto

    @DELETE("posts/delete")
    suspend fun deletePostById(
        @Query("api_key") apiKey: String,
        @Query("post_id") postId: String
    ): PostsResponceDto
}