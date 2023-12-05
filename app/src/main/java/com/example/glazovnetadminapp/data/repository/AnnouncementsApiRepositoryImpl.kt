package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toAnnouncementModel
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.repository.AnnouncementsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.ApiResponseDto
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
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
    override suspend fun getAnnouncements(apiKey: String): Resource<List<AnnouncementModel>> {
        return try {
            val response: ApiResponseDto<List<AnnouncementModelDto>> = client.get("$PATH/getall") {
                parameter("api_key", apiKey)
            }.body()
            Resource.Success(
                data = response.data.map { it.toAnnouncementModel() },
                message = response.message
            )
        } catch (e: Exception) {
            Resource.Error(
                message = e.message.toString()
            )
        }
    }

    override suspend fun createAnnouncement(
        apiKey: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<AnnouncementModel?> {
        return try {
            val response: ApiResponseDto<List<AnnouncementModelDto>> = client.post("$PATH/create") {
                parameter("api_key", apiKey)
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
            Resource.Error(
                message = e.response.toString()
            )
        }
        catch (e: Exception) {
            Resource.Error(
                message = "unknown error"
            )
        }
    }

    override suspend fun deleteAnnouncement(
        apiKey: String,
        announcementId: String
    ): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<List<AnnouncementModelDto>> = client.delete("$PATH/delete") {
                parameter("api_key", apiKey)
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
            Resource.Error(
                message = e.response.toString()
            )
        }
        catch (e: Exception) {
            Resource.Error(
                message = "unknown error"
            )
        }
    }

    override suspend fun updateRepository(
        apiKey: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<Boolean> {
        return try {
            val response: ApiResponseDto<Boolean> = client.put("$PATH/edit") {
                parameter("api_key", apiKey)
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
            Resource.Error(
                message = e.response.toString()
            )
        } catch (e: Exception) {
            Resource.Error(
                message = "unknown error"
            )
        }

    }
}
//TODO: rework exceptions handling. This always throw an Exception or content transformation exception