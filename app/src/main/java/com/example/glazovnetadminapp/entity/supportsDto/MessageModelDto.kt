package com.example.glazovnetadminapp.entity.supportsDto

import com.example.glazovnetadminapp.domain.models.support.MessageModel
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

@Serializable
data class MessageModelDto(
    val id: String,
    val senderId: String,
    val text: String,
    val timestamp: Long
) {
    fun toMessageModel(): MessageModel {
        val sendDateTime = OffsetDateTime.ofInstant(
            Instant.ofEpochSecond(this.timestamp),
            ZoneId.systemDefault()
        )
        return MessageModel(
            id = this.id,
            senderName = this.senderId,
            text = this.text,
            timestamp = sendDateTime
        )
    }
}
