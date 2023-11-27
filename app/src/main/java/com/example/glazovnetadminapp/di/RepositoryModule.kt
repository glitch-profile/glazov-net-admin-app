package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.repository.AddressApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.AnnouncementsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.ClientsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.TariffsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import com.example.glazovnetadminapp.domain.repository.AnnouncementsApiRepository
import com.example.glazovnetadminapp.domain.repository.ClientsApiRepository
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
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

    @Binds
    @Singleton
    abstract fun bindLocalSettingRepository(
        localSettingsRepositoryImpl: LocalSettingsRepositoryImpl
    ): LocalSettingsRepository

    @Binds
    @Singleton
    abstract fun bindAnnouncementsRepository(
        announcementsApiRepositoryImpl: AnnouncementsApiRepositoryImpl
    ): AnnouncementsApiRepository

    @Binds
    @Singleton
    abstract fun bindAddressApiRepository(
        addressApiRepositoryImpl: AddressApiRepositoryImpl
    ): AddressApiRepository

    @Binds
    @Singleton
    abstract fun bindClientsApiRepository(
        clientsApiRepositoryImpl: ClientsApiRepositoryImpl
    ): ClientsApiRepository
}