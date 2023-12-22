package com.example.glazovnetadminapp.data.repository

import android.content.Context
import com.example.glazovnetadminapp.domain.repository.UtilsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.header
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File
import javax.inject.Inject
import javax.inject.Named

private const val PATH = "/api/utils"

class UtilsApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient

): UtilsApiRepository {

    override suspend fun uploadImage(file: File, apiKey: String): Resource<List<String>> {
        return try {
            val response: ApiResponseDto<List<String>> = client.submitFormWithBinaryData(
                url = "$PATH/upload-file",
                formData = formData {
                    append("image", file.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                    })
                }
            ) {
                header("api_key", apiKey)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data
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