package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.entity.announcementsDto.AnnouncementModelDto
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun AnnouncementModelDto.toAnnouncementModel(): AnnouncementModel {
    val announcementCreationDate = OffsetDateTime.parse(creationDate, DateTimeFormatter.ISO_DATE_TIME)
    val filtersList = this.addressFilters.mapNotNull { element ->
        try {
            AddressFilterElement(
                city = element[0],
                street = element[1],
                houseNumber = element[2]
            )
        } catch (e: Exception) {
            null
        }
    }
    return AnnouncementModel(
        id = this.id,
        filters = filtersList,
        title = this.title,
        text = this.text,
        creationDate = announcementCreationDate

    )
}

fun AnnouncementModel.toAnnouncementModelDto(): AnnouncementModelDto {
    val announcementCreationDate = this.creationDate.format(DateTimeFormatter.ISO_DATE_TIME)
    val filtersList = this.filters.map { element ->
        listOf(
            element.city.lowercase(),
            element.street.lowercase(),
            element.houseNumber
        )
    }
    return AnnouncementModelDto(
        id = this.id,
        addressFilters = filtersList,
        title = this.title,
        text = this.text,
        creationDate = announcementCreationDate
    )
}