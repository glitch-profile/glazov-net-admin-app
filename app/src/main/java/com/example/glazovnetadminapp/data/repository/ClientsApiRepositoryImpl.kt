package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toClientModel
import com.example.glazovnetadminapp.domain.models.clients.ClientModel
import com.example.glazovnetadminapp.domain.repository.ClientsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.clientsDto.ClientModelDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
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
        apiKey: String,
        newClient: ClientModelDto
    ): Resource<ClientModel?> {
        return try {
            val response: ApiResponseDto<List<ClientModelDto>> = client.post("$PATH/create") {
                parameter("api_key", apiKey)
                contentType(ContentType.Application.Json)
                setBody(newClient)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.firstOrNull()?.toClientModel(),
                    message = response.message
                )
            } else {
                Resource.Error(
                    message = response.message
                )
            }
        } catch (e: Exception) {
            Resource.Error(
                message = e.localizedMessage ?: "unknown error"
            )
        }
    }

    override suspend fun getClients(apiKey: String): Resource<List<ClientModel>> {
        return try {
            val response: ApiResponseDto<List<ClientModelDto>> = client.get("$PATH/getall") {
                parameter("api_key", apiKey)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.map { it.toClientModel() },
                    message = response.message
                )
            } else {
                Resource.Error(message = response.message)
            }
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "unknown error")
        }
    }

}