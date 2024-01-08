package com.example.glazovnetadminapp.domain.models.support

import java.time.OffsetDateTime

data class MessageModel(
    val id: String = "",
    val senderId: String,
    val isAdmin: Boolean,
    val senderName: String,
    val text: String,
    val timestamp: OffsetDateTime?,
    val isOwnMessage: Boolean = false
)
