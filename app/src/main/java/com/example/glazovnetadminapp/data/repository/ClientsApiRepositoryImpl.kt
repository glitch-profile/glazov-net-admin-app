package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toClientModel
import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.domain.repository.ClientsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.clientsDto.ClientModelDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "api/clients"

class ClientsApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient
): ClientsApiRepository {

    override suspend fun createClient(
        token: String,
        newClient: ClientModelDto
    ): Resource<ClientModel?> {
        return try {
            val response: ApiResponseDto<ClientModelDto?> = client.post("$PATH/create") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(newClient)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data?.toClientModel(),
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

    override suspend fun getClients(token: String): Resource<List<ClientModel>> {
        return try {
            val response: ApiResponseDto<List<ClientModelDto>> = client.get("$PATH/") {
                bearerAuth(token)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.map { it.toClientModel() },
                    message = response.message
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

}