package com.example.glazovnetadminapp.domain.models.announcements

import java.time.OffsetDateTime

data class AnnouncementModel(
    val id: String,
    val filters: List<AddressFilterElement>,
    val title: String,
    val text: String,
    val creationDate: OffsetDateTime? = null
)
