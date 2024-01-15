package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.ImageModel
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.entity.ImageModelDto
import com.example.glazovnetadminapp.entity.postsDto.PostModelDto
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun PostModelDto.toPostModel(): PostModel {
    val postCreationDateTime = OffsetDateTime.ofInstant(
        Instant.ofEpochSecond(this.creationDate),
        ZoneId.systemDefault()
    )
    val imageModel = image?.let {
        ImageModel(
            imageUrl = it.imageUrl,
            imageWidth = it.imageWidth,
            imageHeight = it.imageHeight
        )
    }
    return PostModel(
        postId = id,
        title = title,
        creationDate = postCreationDateTime,
        text = text,
        image = imageModel
    )
}

fun PostModel.toPostModelDto(): PostModelDto {
    val postCreationDateTime = creationDate.toEpochSecond()
    val imageModelDto = image?.let {
        ImageModelDto(
            imageUrl = it.imageUrl,
            imageWidth = it.imageWidth,
            imageHeight = it.imageHeight
        )
    }
    return PostModelDto(
        id = postId,
        title = title,
        creationDate = postCreationDateTime,
        text = text,
        image = imageModelDto
    )
}