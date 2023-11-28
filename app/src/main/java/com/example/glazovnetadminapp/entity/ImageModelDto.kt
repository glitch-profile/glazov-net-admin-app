package com.example.glazovnetadminapp.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class ImageModelDto(
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
)
