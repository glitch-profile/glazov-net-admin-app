package com.example.glazovnetadminapp.data.mappers

import com.example.glazovnetadminapp.domain.models.ImageModel
import com.example.glazovnetadminapp.domain.models.posts.PostModel
import com.example.glazovnetadminapp.domain.models.posts.PostType
import com.example.glazovnetadminapp.domain.models.posts.PostType.Companion.toPostTypeCode
import com.example.glazovnetadminapp.entity.ImageModelDto
import com.example.glazovnetadminapp.entity.postsDto.PostModelDto
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun PostModelDto.toPostModel(): PostModel {
    val postCreationDateTime = OffsetDateTime.parse(creationDate, DateTimeFormatter.ISO_DATE_TIME)
    val postType = PostType.fromPostTypeCode(postTypeCode)
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
        shortDescription = shortDescription,
        fullDescription = fullDescription,
        postType = postType,
        image = imageModel
    )
} //Конвертируем Dto в PostModel с корректными данными

fun PostModel.toPostModelDto(): PostModelDto {
    val postCreationDateTime = creationDate.format(DateTimeFormatter.ISO_DATE_TIME)
    val postTypeCode = postType.toPostTypeCode()
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
        shortDescription = shortDescription,
        fullDescription = fullDescription,
        postTypeCode = postTypeCode,
        image = imageModelDto
    )
} //Конвертируем PostModel в Dto с примитивными данными