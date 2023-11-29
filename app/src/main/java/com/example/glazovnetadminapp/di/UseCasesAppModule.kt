package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.data.repository.AddressApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.AnnouncementsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.ClientsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.LocalSettingsRepositoryImpl
import com.example.glazovnetadminapp.data.repository.PostsApiRepositoryImpl
import com.example.glazovnetadminapp.data.repository.TariffsApiRepositoryImpl
import com.example.glazovnetadminapp.domain.useCases.AddressesUseCase
import com.example.glazovnetadminapp.domain.useCases.AnnouncementsUseCase
import com.example.glazovnetadminapp.domain.useCases.ClientsUseCase
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
        postsApiRepository: PostsApiRepositoryImpl,
        localSettingsRepository: LocalSettingsRepositoryImpl
    ): PostsUseCase {
        return PostsUseCase(
            postsApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideTariffsUseCase(
        tariffsApiRepository: TariffsApiRepositoryImpl,
        localSettingsRepository: LocalSettingsRepositoryImpl
    ): TariffsUseCase {
        return TariffsUseCase(
            tariffsApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideAnnouncementsUseCase(
        announcementsApiRepository: AnnouncementsApiRepositoryImpl,
        localSettingsRepository: LocalSettingsRepositoryImpl
    ): AnnouncementsUseCase {
        return AnnouncementsUseCase(
            announcementsApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideAddressesUseCase(
        addressApiRepository: AddressApiRepositoryImpl,
        localSettingsRepository: LocalSettingsRepositoryImpl
    ): AddressesUseCase {
        return AddressesUseCase(
            addressApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideClientsUseCase(
        localSettingsRepository: LocalSettingsRepositoryImpl,
        clientsApiRepository: ClientsApiRepositoryImpl
    ): ClientsUseCase {
        return ClientsUseCase(
            localSettingsRepository,
            clientsApiRepository
        )
    }

}