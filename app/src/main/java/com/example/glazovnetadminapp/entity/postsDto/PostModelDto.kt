package com.example.glazovnetadminapp.entity.postsDto

import androidx.annotation.Keep
import com.example.glazovnetadminapp.entity.ImageModelDto
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class PostModelDto(
    val id: String = "",
    val title: String,
    val creationDate: String,
    val text: String,
    val postTypeCode: Int,
    val image: ImageModelDto? = null,
)
