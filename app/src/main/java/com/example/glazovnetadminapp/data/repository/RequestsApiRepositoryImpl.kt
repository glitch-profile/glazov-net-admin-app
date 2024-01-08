package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toSupportRequest
import com.example.glazovnetadminapp.domain.models.support.MessageModel
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.RequestsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.supportsDto.MessageModelDto
import com.example.glazovnetadminapp.entity.supportsDto.SupportRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "api/support"

class RequestsApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient,
    @Named("WsClient") private val wsClient: HttpClient,
    private val localSettingsRepository: LocalSettingsRepository
): RequestsApiRepository {

    private var requestsSocket: WebSocketSession? = null
    private var chatSocket: WebSocketSession? = null

    override suspend fun getAllRequests(token: String): Resource<List<SupportRequestModel>> {
        return try {
            val response: ApiResponseDto<List<SupportRequestDto>> = client.get("$PATH/requests") {
                bearerAuth(token)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.map { it.toSupportRequest() }
                )
            } else {
                Resource.Error(message = response.message)
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun getRequestById(
        requestId: String,
        token: String
    ): Resource<SupportRequestModel?> {
        return try {
            val response: ApiResponseDto<SupportRequestDto> = client.get("$PATH/requests/$requestId") {
                bearerAuth(token)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.toSupportRequest()
                )
            } else {
                Resource.Error(response.message)
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun getMessagesForRequest(
        requestId: String,
        token: String
    ): Resource<List<MessageModel>> {
        return try {
            val response: ApiResponseDto<List<MessageModelDto>> = client.get("$PATH/requests/$requestId/messages") {
                bearerAuth(token)
            }.body()
            if (response.status) {
                val messagesList = response.data.map { it.toMessageModel() }
                Resource.Success(
                    data = messagesList.map { message ->
                        message.copy(isOwnMessage = message.senderId == localSettingsRepository.getAssociatedUserId())
                    }
                )
            } else {
                Resource.Error(response.message)
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun addRequest(newRequest: SupportRequestDto, token: String): Resource<SupportRequestModel?> {
        return try {
            val response: ApiResponseDto<SupportRequestDto> = client.post("$PATH/create-request") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(newRequest)
            }.body()
            if (response.status) {
                Resource.Success(data = response.data.toSupportRequest())
            } else {
                Resource.Error(message = response.message)
            }
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun initRequestsSocket(token: String): Resource<Unit> {
        return try {
            requestsSocket = wsClient.webSocketSession {
                url(port = 8080, path = "$PATH/requests-socket")
                bearerAuth(token)
            }
            if (requestsSocket?.isActive == true) {
                Resource.Success(data = Unit)
            } else {
                Resource.Error(message = "couldn't establish a connection")
            }
        } catch (e: ResponseException) {
            Resource.Error(e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error("server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun initChatSocket(requestId: String, token: String): Resource<Unit> {
        return try {
            chatSocket = wsClient.webSocketSession {
                url(port = 8080, path = "$PATH/requests/$requestId/chat-socket")
                bearerAuth(token)
            }
            if (chatSocket?.isActive == true) {
                Resource.Success(Unit)
            } else {
                Resource.Error("couldn't establish a connection")
            }
        } catch (e: ResponseException) {
            Resource.Error(e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error("server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun sendMessage(message: String): Resource<Unit> {
        return try {
            chatSocket?.send(Frame.Text(message))
            Resource.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "unknown error")
        }
    }

    //TODO(Rework the definition of users own message)
    override fun observeMessages(): Flow<MessageModel> {
        return try {
            chatSocket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val encodedMessage = (it as? Frame.Text)?.readText() ?: ""
                    val json = Json { ignoreUnknownKeys = true }
                    val messageDto = json.decodeFromString<MessageModelDto>(encodedMessage)
                    val message = messageDto.toMessageModel()
                    message.copy(isOwnMessage = message.senderId == localSettingsRepository.getLoginToken())
                } ?: flow{}
        } catch (e: Exception) {
            e.printStackTrace()
            flow { }
        }
    }

    override fun observeRequests(): Flow<SupportRequestModel> {
        return try {
            requestsSocket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val stringRequests = (it as? Frame.Text)?.readText() ?: ""
                    val json = Json {
                        ignoreUnknownKeys = true
                    }
                    val requestDto = json.decodeFromString<SupportRequestDto>(stringRequests)
                    requestDto.toSupportRequest()
                } ?: flow{}
        } catch (e: Exception) {
            e.printStackTrace()
            flow {}
        }
    }

    override suspend fun closeRequestsConnection() {
        requestsSocket?.close()
    }

    override suspend fun closeChatConnection() {
        chatSocket?.close()
    }
}
