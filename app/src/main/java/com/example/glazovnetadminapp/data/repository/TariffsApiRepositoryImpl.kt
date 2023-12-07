package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toTariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.repository.TariffsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.tariffsDto.TariffModelDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "api/tariffs"

class TariffsApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient
): TariffsApiRepository {
    override suspend fun getAllTariffs(): Resource<List<TariffModel>> {
        return try {
            val response: ApiResponseDto<List<TariffModelDto>> = client.get("$PATH/getall").body()
            if (response.status) {
                Resource.Success(
                    data = response.data.map{ it.toTariffModel() },
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

    override suspend fun addTariff(apiKey: String, tariff: TariffModelDto): Resource<TariffModel?> {
        return try {
            val response: ApiResponseDto<List<TariffModelDto>> = client.post("$PATH/add") {
                header("api_key", apiKey)
                contentType(ContentType.Application.Json)
                setBody(tariff)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.firstOrNull()?.toTariffModel(),
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

    override suspend fun deleteTariff(apiKey: String, tariffId: String): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<List<TariffModelDto>> = client.delete("$PATH/remove") {
                header("api_key", apiKey)
                parameter("tariff_id", tariffId)
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

    override suspend fun updateTariff(apiKey: String, tariff: TariffModelDto): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<List<TariffModelDto>> = client.put("$PATH/edit") {
                header("api_key", apiKey)
                contentType(ContentType.Application.Json)
                setBody(tariff)
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