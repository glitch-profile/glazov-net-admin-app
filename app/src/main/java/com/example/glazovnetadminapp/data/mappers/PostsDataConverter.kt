package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.posts.PostModel
import com.example.glazovnetadminapp.domain.posts.PostType
import com.example.glazovnetadminapp.entity.PostModelDto
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun PostModelDto.toPostModel(): PostModel {
    val postCreationDateTime = OffsetDateTime.parse(creationDate, DateTimeFormatter.ISO_DATE_TIME)
    val postType = PostType.fromPostTypeCode(postTypeCode)
    return PostModel(
        postId = id,
        title = title,
        creationDate = postCreationDateTime,
        shortDescription = shortDescription,
        fullDescription = fullDescription,
        postType = postType,
        imageUrl = imageUrl,
        videoUrl = videoUrl
    )
} //Конвертируем Dto в PostModel с корректными данными

fun PostModel.toPostModelDto(): PostModelDto {
    val postCreationDateTime = creationDate.format(DateTimeFormatter.ISO_DATE_TIME)
    val postTypeCode = PostType.toPostTypeCode(postType = postType)
    return PostModelDto(
        id = postId,
        title = title,
        creationDate = postCreationDateTime,
        shortDescription = shortDescription,
        fullDescription = fullDescription,
        postTypeCode = postTypeCode,
        imageUrl = imageUrl,
        videoUrl = videoUrl
    )
} //Конвертируем PostModel в Dto с примитивными данными