package com.example.glazovnetadminapp.entity

import com.squareup.moshi.Json

data class ImageModelDto(
    @field:Json(name = "imageUrl")
    val imageUrl: String,
    @field:Json(name = "imageWidth")
    val imageWidth: Int,
    @field:Json(name = "imageHeight")
    val imageHeight: Int
)
