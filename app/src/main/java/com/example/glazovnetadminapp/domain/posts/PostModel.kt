package com.example.glazovnetadminapp.domain.posts

import java.time.OffsetDateTime

data class PostModel(
    val postId: String,
    val title: String,
    val creationDate: OffsetDateTime,
    val shortDescription: String? = null,
    val fullDescription: String,
    val postType: PostType,
    val imageUrl: String? = null,
    val videoUrl: String? = null
)
