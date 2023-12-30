package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto

interface AnnouncementsApiRepository {

    suspend fun getAnnouncements(
        token: String
    ): Resource<List<AnnouncementModel>>

    suspend fun createAnnouncement(
        token: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<AnnouncementModel?>

    suspend fun deleteAnnouncement(
        token: String,
        announcementId: String
    ): Resource<Boolean>

    suspend fun updateRepository(
        token: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<Boolean>

}