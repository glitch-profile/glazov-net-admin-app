package com.example.glazovnetadminapp.entity.announcementsDto

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class AnnouncementModelDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "addressFilters")
    val addressFilters: List<List<String>>,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "text")
    val text: String,
    @field:Json(name = "creationDate")
    val creationDate: String
)
