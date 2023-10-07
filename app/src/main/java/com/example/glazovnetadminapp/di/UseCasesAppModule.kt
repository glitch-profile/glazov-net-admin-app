package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.TariffsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import com.example.glazovnetadminapp.domain.useCases.TariffsUseCase
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
    fun providePostsUseCase(
        postsApiRepository: PostsApiRepositoryImpl
    ): PostsUseCase {
        return PostsUseCase(postsApiRepository)
    }

    @Provides
    @Singleton
    fun provideTariffsUseCase(
        tariffsApiRepository: TariffsApiRepositoryImpl
    ): TariffsUseCase {
        return TariffsUseCase(tariffsApiRepository)
    }

}