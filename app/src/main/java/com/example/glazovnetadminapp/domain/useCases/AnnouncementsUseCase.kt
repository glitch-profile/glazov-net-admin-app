package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toAnnouncementModelDto
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.repository.AnnouncementsApiRepository
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class AnnouncementsUseCase @Inject constructor(
    private val announcementsApiRepository: AnnouncementsApiRepository,
    private val localSettingsRepository: LocalSettingsRepository
) {

    suspend fun getAnnouncements(): Resource<List<AnnouncementModel>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return announcementsApiRepository.getAnnouncements(
            token = token
        )
    }

    suspend fun createAnnouncement(
        announcement: AnnouncementModel
    ): Resource<AnnouncementModel?> {
        val announcementDto = announcement.toAnnouncementModelDto()
        val token = localSettingsRepository.getLoginToken() ?: ""
        return announcementsApiRepository.createAnnouncement(
            token = token,
            newAnnouncement = announcementDto
        )
    }

    suspend fun deleteAnnouncement(
        announcementId: String
    ): Resource<Boolean> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return announcementsApiRepository.deleteAnnouncement(
            token = token,
            announcementId = announcementId
        )
    }

    suspend fun updateAnnouncement(
        newAnnouncement: AnnouncementModel
    ): Resource<Boolean> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        val announcementDto = newAnnouncement.toAnnouncementModelDto()
        return announcementsApiRepository.updateRepository(
            token = token,
            newAnnouncement = announcementDto
        )
    }

}