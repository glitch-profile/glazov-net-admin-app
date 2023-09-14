package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.useCases.PostsEditUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesAppModule {

    @Provides
    @Singleton
    fun providePostsEditUseCase(
        postsApiRepository: PostsApiRepositoryImpl
    ): PostsEditUseCase {
        return PostsEditUseCase(postsApiRepository)
    }

}