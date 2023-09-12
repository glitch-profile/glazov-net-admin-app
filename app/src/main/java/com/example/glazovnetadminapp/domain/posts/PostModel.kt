package com.example.glazovnetadminapp.domain.posts

import java.time.LocalDateTime

data class PostModel(
    val postId: String,
    val title: String,
    val creationDate: LocalDateTime,
    val shortDescription: String,
    val fullDescription: String,
    val postType: PostType,
    val imageUrl: String?,
    val videoUrl: String?
)
