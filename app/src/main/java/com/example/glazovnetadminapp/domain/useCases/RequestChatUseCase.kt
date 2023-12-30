package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.models.support.MessageModel
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.RequestsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RequestChatUseCase @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository,
    private val requestsApiRepository: RequestsApiRepository
) {

    suspend fun getRequestById(requestId: String): Resource<SupportRequestModel?> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return requestsApiRepository.getRequestById(requestId, token)
    }

    suspend fun getMessagesForRequest(requestId: String): Resource<List<MessageModel>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return requestsApiRepository.getMessagesForRequest(requestId, token)
    }

    suspend fun initChatSocket(requestId: String): Resource<Unit> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return requestsApiRepository.initChatSocket(requestId, token)
    }

    suspend fun sendMessage(messageText: String): Resource<Unit> {
        return requestsApiRepository.sendMessage(messageText)
    }

    fun observeMessages() = requestsApiRepository.observeMessages()

    suspend fun disconnect() = requestsApiRepository.closeChatConnection()

}