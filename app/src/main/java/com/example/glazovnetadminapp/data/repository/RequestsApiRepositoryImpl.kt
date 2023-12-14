package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toSupportRequest
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.repository.RequestsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.supportsDto.SupportRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.websocket.webSocketSession
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
import kotlinx.coroutines.flow.emptyFlow
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
    @Named("WsClient") private val wsClient: HttpClient
): RequestsApiRepository {

    private var socket: WebSocketSession? = null

    override suspend fun getAllRequests(apiKey: String): Resource<List<SupportRequestModel>> {
        return try {
            val response: ApiResponseDto<List<SupportRequestDto>> = client.get("$PATH/requests") {
                header("api_key", apiKey)
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

    override suspend fun addRequest(newRequest: SupportRequestDto): Resource<SupportRequestModel?> {
        return try {
            val response: ApiResponseDto<SupportRequestDto> = client.post("$PATH/createrequest") {
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

    override suspend fun initSocket(memberId: String): Resource<Unit> {
        return try {
            socket = wsClient.webSocketSession {
                url("$PATH/requests-socket")
                header("memberId", memberId)
            }
            if (socket?.isActive == true) {
                Resource.Success(data = Unit)
            } else {
                Resource.Error(message = "couldn't establish a connection")
            }
        } catch (e: ResponseException) {
            Resource.Error(e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(e.message.toString())
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override fun observeRequests(): Flow<SupportRequestModel> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val requestDto = Json.decodeFromString<SupportRequestDto>(json)
                    requestDto.toSupportRequest()
                } ?: flow{}
        } catch (e: Exception) {
            e.printStackTrace()
            flow {}
        }
    }

    override suspend fun closeConnection() {
        socket?.close()
    }

}