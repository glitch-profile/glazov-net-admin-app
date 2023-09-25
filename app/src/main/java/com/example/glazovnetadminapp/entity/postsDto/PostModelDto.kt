package com.example.glazovnetadminapp.entity.postsDto

import androidx.annotation.Keep
import com.example.glazovnetadminapp.entity.ImageModelDto
import com.squareup.moshi.Json

@Keep
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
    @field:Json(name = "postTypeCode")
    val postTypeCode: Int,
    @field:Json(name = "image")
    val image: ImageModelDto? = null,
)
