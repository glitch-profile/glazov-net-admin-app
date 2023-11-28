package com.example.glazovnetadminapp.domain.models.posts

import com.example.glazovnetadminapp.domain.models.ImageModel
import java.time.OffsetDateTime

data class PostModel(
    val postId: String,
    val title: String,
    val creationDate: OffsetDateTime,
    val shortDescription: String? = null,
    val fullDescription: String,
    val postType: PostType,
    val image: ImageModel? = null,
)