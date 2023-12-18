package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.RequestsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class RequestChatUseCase @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository,
    private val requestsApiRepository: RequestsApiRepository
) {

    //TODO: Add auth security
    suspend fun getRequestById(requestId: String): Resource<SupportRequestModel?> {
//        val memberId = localSettingsRepository...
        return requestsApiRepository.getRequestById(requestId, "123456")
    }

    suspend fun initChatSocket(requestId: String): Resource<Unit> {
//        val memberId = localSettingsRepository...
        return requestsApiRepository.initChatSocket(requestId, "123456")
    }

    suspend fun sendMessage(messageText: String): Resource<Unit> {
        return requestsApiRepository.sendMessage(messageText)
    }

    fun observeMessages() = requestsApiRepository.observeMessages()

    suspend fun disconnect() = requestsApiRepository.closeChatConnection()

}