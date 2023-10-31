package com.example.glazovnetadminapp.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ApiResponseDto<T>(
    @field:Json(name = "status")
    val status: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "data")
    val data: T
)
