package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

//private const val BASE_URL = "http://82.179.121.126:8080/" //notebook
private const val BASE_URL = "http://146.120.105.211:8080/" //computer

@Module
@InstallIn(SingletonComponent::class)
object ApiAppModule {

    @Provides
    @Singleton
    fun provideGlazovNetPostsApi(): GlazovNetApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

}