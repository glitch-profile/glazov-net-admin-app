package com.example.glazovnetadminapp.di

import com.example.glazovnetadminapp.domain.repository.AddressApiRepository
import com.example.glazovnetadminapp.domain.repository.AnnouncementsApiRepository
import com.example.glazovnetadminapp.domain.repository.ClientsApiRepository
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.PostsApiRepository
import com.example.glazovnetadminapp.domain.repository.RequestsApiRepository
import com.example.glazovnetadminapp.domain.repository.TariffsApiRepository
import com.example.glazovnetadminapp.domain.repository.UtilsApiRepository
import com.example.glazovnetadminapp.domain.useCases.AddressesUseCase
import com.example.glazovnetadminapp.domain.useCases.AnnouncementsUseCase
import com.example.glazovnetadminapp.domain.useCases.ClientsUseCase
import com.example.glazovnetadminapp.domain.useCases.PostsUseCase
import com.example.glazovnetadminapp.domain.useCases.RequestChatUseCase
import com.example.glazovnetadminapp.domain.useCases.RequestsUseCase
import com.example.glazovnetadminapp.domain.useCases.TariffsUseCase
import com.example.glazovnetadminapp.domain.useCases.UtilsUseCase
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
        postsApiRepository: PostsApiRepository,
        localSettingsRepository: LocalSettingsRepository
    ): PostsUseCase {
        return PostsUseCase(
            postsApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideTariffsUseCase(
        tariffsApiRepository: TariffsApiRepository,
        localSettingsRepository: LocalSettingsRepository
    ): TariffsUseCase {
        return TariffsUseCase(
            tariffsApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideAnnouncementsUseCase(
        announcementsApiRepository: AnnouncementsApiRepository,
        localSettingsRepository: LocalSettingsRepository
    ): AnnouncementsUseCase {
        return AnnouncementsUseCase(
            announcementsApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideAddressesUseCase(
        addressApiRepository: AddressApiRepository,
        localSettingsRepository: LocalSettingsRepository
    ): AddressesUseCase {
        return AddressesUseCase(
            addressApiRepository,
            localSettingsRepository
        )
    }

    @Provides
    @Singleton
    fun provideClientsUseCase(
        localSettingsRepository: LocalSettingsRepository,
        clientsApiRepository: ClientsApiRepository
    ): ClientsUseCase {
        return ClientsUseCase(
            localSettingsRepository,
            clientsApiRepository
        )
    }

    @Provides
    @Singleton
    fun provideRequestsUseCase(
        localSettingsRepository: LocalSettingsRepository,
        requestsApiRepository: RequestsApiRepository
    ): RequestsUseCase {
        return RequestsUseCase(
            localSettingsRepository,
            requestsApiRepository
        )
    }

    @Provides
    @Singleton
    fun provideRequestChatUseCase(
        localSettingsRepository: LocalSettingsRepository,
        requestsApiRepository: RequestsApiRepository
    ): RequestChatUseCase {
        return RequestChatUseCase(
            localSettingsRepository,
            requestsApiRepository
        )
    }

    @Provides
    @Singleton
    fun provideUtilsUseCase(
        utilsApiRepository: UtilsApiRepository,
        localSettingsRepository: LocalSettingsRepository
    ): UtilsUseCase {
        return UtilsUseCase(
            utilsApiRepository = utilsApiRepository,
            localSettingsRepository = localSettingsRepository
        )
    }
}