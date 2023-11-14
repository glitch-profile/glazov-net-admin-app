package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toAnnouncementModelDto
import com.example.glazovnetadminapp.data.repository.AddressApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.AnnouncementsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class AnnouncementsUseCase @Inject constructor(
    private val announcementsApiRepository: AnnouncementsApiRepositoryImpl,
    private val addressApiRepository: AddressApiRepositoryImpl,
    private val localSettingsRepository: LocalSettingsRepositoryImpl
) {

    suspend fun getAnnouncements(): Resource<List<AnnouncementModel>> {
        return announcementsApiRepository.getAnnouncements()
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

    suspend fun getCitiesList(
        cityName: String
    ): List<String> {
        val apiKey = localSettingsRepository.getSavedApiKey()
        return addressApiRepository.getCitiesWithName(
            cityName = cityName,
            apiKey = apiKey
        )
    }

    suspend fun getStreetsList(
        cityName: String,
        streetName: String
    ): List<String> {
        return if (cityName.isNotBlank() && streetName.isNotBlank()) {
            val apiKey = localSettingsRepository.getSavedApiKey()
            addressApiRepository.getStreetsWithName(
                cityName = cityName,
                streetName = streetName,
                apiKey = apiKey
            )
        } else emptyList()
    }

}