package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto
import com.example.glazovnetadminapp.entity.filtersDto.FilterModelDto

interface AnnouncementsApiRepository {

    suspend fun createAnnouncement(
        apiKey: String,
        newAnnouncement: AnnouncementModelDto
    ): Resource<AnnouncementModel?>

    suspend fun deleteAnnouncement(
        apiKey: String,
        announcementId: String
    ): Resource<Boolean>

    suspend fun createFilter(
        apiKey: String,
        addressFilter: FilterModelDto
    ): Resource<FilterModelDto?>

    suspend fun deleteFilter(
        apiKey: String,
        addressFilterId: String
    ): Resource<Boolean>

    suspend fun getFilters(
        limit: Int? = null
    ): Resource<List<FilterModelDto>>

}