package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.support.MessageModel
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
        creationDate = creationTime,
        isSolved = isSolved
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
        creationDate = creationTimeLong,
        isSolved = this.isSolved
    )
}