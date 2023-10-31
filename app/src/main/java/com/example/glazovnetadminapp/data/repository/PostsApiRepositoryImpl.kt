package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toPostModel
import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.repository.PostsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.postsDto.PostModelDto
import javax.inject.Inject

class PostsApiRepositoryImpl @Inject constructor(
    private val api: GlazovNetApi
): PostsApiRepository {
    override suspend fun getAllPosts(): Resource<List<PostModel?>> {
        return try {
            val result = api.getAllPosts()
            if (result.status) {
                Resource.Success(
                    data = result.data.map { it?.toPostModel() },
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
                Resource.Success(
                    data = result.data.map { it.toPostModel() },
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
                Resource.Success(
                    data = result.data.firstOrNull()?.toPostModel(),
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

    override suspend fun addPost(apiKey: String, postModel: PostModelDto): Resource<PostModel?> {
        return try {
            val result = api.addPost(postModel = postModel, apiKey = apiKey)
            if (result.status) {
                Resource.Success(
                    data = result.data.firstOrNull()?.toPostModel(),
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