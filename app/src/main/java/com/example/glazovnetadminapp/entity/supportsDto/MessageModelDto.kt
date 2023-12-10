package com.example.glazovnetadminapp.entity.supportsDto

import kotlinx.serialization.Serializable

@Serializable
data class MessageModelDto(
    val senderName: String,
    val text: String,
    val timestamp: Long
)
