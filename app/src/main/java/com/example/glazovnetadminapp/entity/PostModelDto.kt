package com.example.glazovnetadminapp.entity

import com.squareup.moshi.Json

data class PostModelDto(
    @field:Json(name = "id")
    val id: String = "",
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "creationDate")
    val creationDate: String,
    @field:Json(name = "shortDescription")
    val shortDescription: String? = null,
    @field:Json(name = "fullDescription")
    val fullDescription: String,
    @field:Json(name = "postType")
    val postTypeCode: Int,
    @field:Json(name = "imageUrl")
    val imageUrl: String? = null,
    @field:Json(name = "videoUrl")
    val videoUrl: String? = null
)
