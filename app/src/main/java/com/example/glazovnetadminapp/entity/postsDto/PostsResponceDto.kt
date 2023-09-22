package com.example.glazovnetadminapp.entity.postsDto

import com.squareup.moshi.Json

data class PostsResponceDto(
    @field:Json(name = "status")
    val status: Boolean,
    @field:Json(name = "message")
    val message: String,
    @field:Json(name = "data")
    val data: List<PostModelDto?>
)
