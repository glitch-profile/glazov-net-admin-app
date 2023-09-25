package com.example.glazovnetadminapp.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ImageModelDto(
    @field:Json(name = "imageUrl")
    val imageUrl: String,
    @field:Json(name = "imageWidth")
    val imageWidth: Int,
    @field:Json(name = "imageHeight")
    val imageHeight: Int
)
