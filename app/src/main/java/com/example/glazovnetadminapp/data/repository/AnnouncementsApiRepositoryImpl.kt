package com.example.glazovnetadminapp.data.repository

import com.example.glazovnetadminapp.data.mappers.toAnnouncementModel
import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.repository.AnnouncementsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto
import javax.inject.Inject

class AnnouncementsApiRepositoryImpl @Inject constructor(
    private val api: GlazovNetApi
): AnnouncementsApiRepository {
    override suspend fun getAnnouncements(): Resource<List<AnnouncementModel>> {
        return try {
            val result = api.getAnnouncements()
            Resource.Success(
                data = result.data.map { it.toAnnouncementModel() },
                message = result.message
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
            val result = api.createNewAnnouncement(
                apiKey,
                newAnnouncement
            )
            if (result.status) {
                Resource.Success(
                    data = result.data.firstOrNull()?.toAnnouncementModel(),
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

    override suspend fun deleteAnnouncement(
        apiKey: String,
        announcementId: String
    ): Resource<Boolean> {
        return try {
            val result = api.deleteAnnouncement(
                apiKey,
                announcementId
            )
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