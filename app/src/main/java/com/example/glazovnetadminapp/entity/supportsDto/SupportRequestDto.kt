package com.example.glazovnetadminapp.entity.supportsDto

import kotlinx.serialization.Serializable

@Serializable
data class SupportRequestDto(
    val id: String,
    val creatorId: String,
    val associatedSupportId: String?,
    val title: String,
    val description: String,
    val messages: List<MessageModelDto>,
    val creationDate: Long,
    val status: Int
)
