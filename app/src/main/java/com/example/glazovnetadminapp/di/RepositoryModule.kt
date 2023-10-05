package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.TariffsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.repository.PostsApiRepository
import com.example.glazovnetadminapp.domain.repository.TariffsApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPostsApiRepository(
        postsApiRepositoryImpl: PostsApiRepositoryImpl
    ): PostsApiRepository

    @Binds
    @Singleton
    abstract fun bindTariffsApiRepository(
        tariffsApiRepositoryImpl: TariffsApiRepositoryImpl
    ): TariffsApiRepository
}