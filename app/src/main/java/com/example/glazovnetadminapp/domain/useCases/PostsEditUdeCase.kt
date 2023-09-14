package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import javax.inject.Inject

class PostsEditUseCase @Inject constructor(
    val postsApiRepositoryImpl: PostsApiRepositoryImpl
) {



}