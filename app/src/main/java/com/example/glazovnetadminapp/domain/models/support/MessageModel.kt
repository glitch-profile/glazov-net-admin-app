package com.example.glazovnetadminapp.domain.models.support

import com.example.glazovnetadminapp.entity.supportsDto.MessageModelDto
import java.time.OffsetDateTime

data class MessageModel(
    val senderName: String,
    val text: String,
    val timestamp: OffsetDateTime?
) {
    fun toMessageModelDto(): MessageModelDto {
        val creationTimeLong = timestamp?.toEpochSecond() ?: 0L
        return MessageModelDto(
            senderName = senderName,
            text = text,
            timestamp = creationTimeLong
        )
    }
}
