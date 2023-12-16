package com.example.glazovnetadminapp.domain.models.support

import java.time.OffsetDateTime

data class SupportRequestModel(
    val id: String = "",
    val creatorId: String,
    val associatedSupportId: String? = null,
    val title: String,
    val description: String,
//    val messages: List<MessageModel> = emptyList(),
    val creationDate: OffsetDateTime? = null,
    val status: Int = 0
)
