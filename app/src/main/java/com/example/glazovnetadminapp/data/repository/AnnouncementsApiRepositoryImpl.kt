package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toAnnouncementModel
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.repository.AnnouncementsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto
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

private const val PATH = "api/announcements"

class AnnouncementsApiRepositoryImpl @Inject constructor(
    @Named("RestClient") private val client: HttpClient
): AnnouncementsApiRepository {
    override suspend fun getAnnouncements(token: String): Resource<List<AnnouncementModel>> {
        return try {
            val response: ApiResponseDto<List<AnnouncementModelDto>> = client.get("$PATH/") {
                bearerAuth(token)
            }.body()
            Resource.Success(
                data = response.data.map { it.toAnnouncementModel() },
                message = response.message
            )
        } catch (e: ResponseException) {
            Resource.Error(message = e.response.status.toString())
        } catch (e: ConnectTimeoutException) {
            Resource.Error(message = "server not available")
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "unknown error")
        }
    }

    override suspend fun createAnnouncement(
        token: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<AnnouncementModel?> {
        return try {
            val response: ApiResponseDto<List<AnnouncementModelDto>> = client.post("$PATH/create") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(newAnnouncement)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data.firstOrNull()?.toAnnouncementModel(),
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

    override suspend fun deleteAnnouncement(
        token: String,
        announcementId: String
    ): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<List<AnnouncementModelDto>> = client.delete("$PATH/delete") {
                bearerAuth(token)
                parameter("id", announcementId)
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

    override suspend fun updateRepository(
        token: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<Boolean> = client.put("$PATH/edit") {
                bearerAuth(token)
                contentType(ContentType.Application.Json)
                setBody(newAnnouncement)
            }.body()
            if (response.status) {
                Resource.Success(
                    data = response.data,
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