package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toPostModel
import com.example.glazovnetadminapp.data.remote.GlazovNetPostsApi
import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.repository.PostsRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.PostModelDto
import com.example.glazovnetadminapp.entity.PostsResponceDto
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val api: GlazovNetPostsApi
): PostsRepository {
    override suspend fun getAllPosts(): Resource<List<PostModel?>> {
        return try {
            val result = api.getAllPosts()
            if (result.status) {
                val data = result.data.map { it?.toPostModel() }
                Resource.Success(
                    data = data,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun getPostsList(limit: Int?, startIndex: Int?): Resource<List<PostModel?>> {
        return try {
            val result = api.getPostsList(limit = limit, startIndex = startIndex)
            if (result.status) {
                val data = result.data.map { it?.toPostModel() }
                Resource.Success(
                    data = data,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun getPostById(postId: String): Resource<PostModel?> {
        return try {
            val result = api.getPostById(postId = postId)
            if (result.status) {
                val data = result.data[0]?.toPostModel()
                Resource.Success(
                    data = data,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun addPost(apiKey: String, postModel: PostModelDto): Resource<Boolean> {
        return try {
            val result = api.addPost(postModel = postModel, apiKey = apiKey)
            if (result.status) {
                Resource.Success(
                    data = true,
                    message = result.message
                )
            } else {
                Resource.Error(
                    result.message
                )
            }
        } catch (e:Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun editPost(apiKey: String, postModel: PostModelDto): Resource<Boolean> {
        return try {
            val result = api.editPost(postModel = postModel, apiKey = apiKey)
            if (result.status) {
                Resource.Success(
                    data = true,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun deletePostById(apiKey: String, postId: String): Resource<Boolean> {
        return try {
            val result = api.deletePostById(apiKey = apiKey, postId = postId)
            if (result.status) {
                Resource.Success(
                    data = true,
                    message = result.message
                )
            } else {
                Resource.Error(
                    message = result.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }
}