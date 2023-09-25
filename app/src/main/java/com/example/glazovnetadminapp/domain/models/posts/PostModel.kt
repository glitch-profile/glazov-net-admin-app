package com.example.glazovnetadminapp.domain.models.posts

import androidx.annotation.Keep
import com.example.glazovnetadminapp.domain.models.ImageModel
import java.time.OffsetDateTime

@Keep //Необходимо для того, что бы ProGuard не обрабатывал дата классы
data class PostModel(
    val postId: String,
    val title: String,
    val creationDate: OffsetDateTime,
    val shortDescription: String? = null,
    val fullDescription: String,
    val postType: PostType,
    val image: ImageModel? = null,
)