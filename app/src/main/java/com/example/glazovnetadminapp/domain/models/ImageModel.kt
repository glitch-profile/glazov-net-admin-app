package com.example.glazovnetadminapp.domain.models

import androidx.annotation.Keep

@Keep
data class ImageModel(
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int
)
