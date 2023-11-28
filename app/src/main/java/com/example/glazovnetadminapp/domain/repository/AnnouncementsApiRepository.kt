package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto

interface AnnouncementsApiRepository {

    suspend fun getAnnouncements(
        apiKey: String
    ): Resource<List<AnnouncementModel>>

    suspend fun createAnnouncement(
        apiKey: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<AnnouncementModel?>

    suspend fun deleteAnnouncement(
        apiKey: String,
        announcementId: String
    ): Resource<Boolean>

}