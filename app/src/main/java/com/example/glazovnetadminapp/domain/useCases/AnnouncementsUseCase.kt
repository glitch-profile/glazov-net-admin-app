package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toAnnouncementModelDto
import com.example.glazovnetadminapp.data.repository.AddressApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.AnnouncementsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class AnnouncementsUseCase @Inject constructor(
    private val announcementsApiRepository: AnnouncementsApiRepositoryImpl,
    private val localSettingsRepository: LocalSettingsRepositoryImpl
) {

    suspend fun getAnnouncements(): Resource<List<AnnouncementModel>> {
        val apiKey = localSettingsRepository.getSavedApiKey()
        return announcementsApiRepository.getAnnouncements(
            apiKey = apiKey
        )
    }

    suspend fun createAnnouncement(
        announcement: AnnouncementModel
    ): Resource<AnnouncementModel?> {
        val announcementDto = announcement.toAnnouncementModelDto()
        val apiKey = localSettingsRepository.getSavedApiKey()
        return announcementsApiRepository.createAnnouncement(
            apiKey = apiKey,
            newAnnouncement = announcementDto
        )
    }

    suspend fun deleteAnnouncement(
        announcementId: String
    ): Resource<Boolean> {
        val apiKey = localSettingsRepository.getSavedApiKey()
        return announcementsApiRepository.deleteAnnouncement(
            apiKey = apiKey,
            announcementId = announcementId
        )
    }

}