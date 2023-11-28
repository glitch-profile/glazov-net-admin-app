package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.remote.GlazovNetApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

private const val BASE_URL = "http://82.179.120.234:8080/" //notebook
//private const val BASE_URL = "http://146.120.105.211:8080/" //computer

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

    @Provides
    @Singleton
    @Named("RestClient")
    fun provideGlazovNetKtorClient(): HttpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(DefaultRequest) {
            url(BASE_URL)
        }
    }

}