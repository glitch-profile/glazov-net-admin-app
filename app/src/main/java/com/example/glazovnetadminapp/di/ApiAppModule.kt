package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.remote.GlazovNetPostsApi
import com.example.glazovnetadminapp.data.remote.GlazovNetTariffsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

//private const val BASE_URL = "http://192.168.1.215:8080/"
private const val BASE_URL = "http://192.168.0.18:8080/"

@Module
@InstallIn(SingletonComponent::class)
object ApiAppModule {

    @Provides
    @Singleton
    fun provideGlazovNetPostsApi(): GlazovNetPostsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideGlazovNetTariffsApi(): GlazovNetTariffsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

}