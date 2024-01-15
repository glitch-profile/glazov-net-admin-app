package com.example.glazovnetadminapp.entity.postsDto

import androidx.annotation.Keep
import com.example.glazovnetadminapp.entity.ImageModelDto
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class PostModelDto(
    val id: String = "",
    val title: String,
    val creationDate: Long,
    val text: String,
    val image: ImageModelDto? = null,
)
