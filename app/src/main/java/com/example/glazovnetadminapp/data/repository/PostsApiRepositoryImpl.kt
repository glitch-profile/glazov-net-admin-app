package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toPostModel
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.repository.PostsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.postsDto.PostModelDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "api/posts"

class PostsApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient
): PostsApiRepository {

    override suspend fun getAllPosts(
        token: String
    ): Resource<List<PostModel>> {
        return try {
            val response: ApiResponseDto<List<PostModelDto>> = client.get("$PATH/") {
                bearerAuth(token)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.map { it.toPostModel() },
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun getPostsList(limit: Int?, startIndex: Int?, token: String): Resource<List<PostModel>> {
        return try {
            val response: ApiResponseDto<List<PostModelDto>> = client.get("$PATH/list") {
                bearerAuth(token)
                parameter("limit", limit)
                parameter("start_index", startIndex)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.map { it.toPostModel() },
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun getPostById(postId: String, token: String): Resource<PostModel?> {
        return try {
            val response: ApiResponseDto<List<PostModelDto>> = client.get("$PATH/$postId"){
                bearerAuth(token)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.firstOrNull()?.toPostModel(),
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun addPost(postModel: PostModelDto, token: String): Resource<PostModel?> {
        return try {
            val response: ApiResponseDto<List<PostModelDto>> = client.post("$PATH/add") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(postModel)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.firstOrNull()?.toPostModel(),
                    message = response.message
                )
            } else {
                Resource.Error(
                    response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun editPost(postModel: PostModelDto, token: String): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<List<PostModelDto>> = client.put("$PATH/edit") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(postModel)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = true,
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun deletePostById(postId: String, token: String): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<List<PostModelDto>> = client.delete("$PATH/delete") {
                bearerAuth(token)
                parameter("post_id", postId)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = true,
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }
}