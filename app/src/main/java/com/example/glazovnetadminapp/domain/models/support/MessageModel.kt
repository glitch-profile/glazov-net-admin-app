package com.example.glazovnetadminapp.domain.models.support

import com.example.glazovnetadminapp.entity.supportsDto.MessageModelDto
import java.time.OffsetDateTime

data class MessageModel(
    val id: String = "",
    val senderName: String,
    val text: String,
    val timestamp: OffsetDateTime?,
    val isOwnMessage: Boolean = false
) {
    fun toMessageModelDto(): MessageModelDto {
        val creationTimeLong = timestamp?.toEpochSecond() ?: 0L
        return MessageModelDto(
            id = this.id,
            senderId = senderName,
            text = text,
            timestamp = creationTimeLong
        )
    }
}
