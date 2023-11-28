package com.example.glazovnetadminapp.entity.announcementsDto

import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class AnnouncementModelDto(
    val id: String,
    val addressFilters: List<List<String>>,
    val title: String,
    val text: String,
    val creationDate: String
)
