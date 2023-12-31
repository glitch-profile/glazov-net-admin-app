package com.example.glazovnetadminapp.entity

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class ApiResponseDto<T>(
    val status: Boolean,
    val message: String,
    val data: T
)
