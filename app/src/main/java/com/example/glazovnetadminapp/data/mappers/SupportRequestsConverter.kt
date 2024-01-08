package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.entity.supportsDto.MessageModelDto
import com.example.glazovnetadminapp.entity.supportsDto.SupportRequestDto
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId

fun SupportRequestDto.toSupportRequest(): SupportRequestModel {
    val creationTime = OffsetDateTime.ofInstant(
        Instant.ofEpochSecond(this.creationDate),
        ZoneId.systemDefault()
    )
    return SupportRequestModel(
        id = this.id,
        creatorId = this.creatorId,
        associatedSupportId = this.associatedSupportId,
        title = this.title,
        description = this.description,
        messages = this.messages.map { it.toMessageModel() },
        creationDate = creationTime,
        status = this.status
    )
}

fun SupportRequestModel.toSupportRequestDto(): SupportRequestDto {
    val creationTimeLong = this.creationDate?.toEpochSecond() ?: 0L
    return SupportRequestDto(
        id = this.id,
        creatorId = this.creatorId,
        associatedSupportId = this.associatedSupportId,
        title = this.title,
        description = this.description,
        messages = emptyList(),
        creationDate = creationTimeLong,
        status = this.status
    )
}