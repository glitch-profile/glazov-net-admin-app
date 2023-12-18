package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.domain.models.support.MessageModel
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.RequestsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RequestChatUseCase @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository,
    private val requestsApiRepository: RequestsApiRepository
) {

    //TODO: Add auth security
    suspend fun getRequestById(requestId: String): Resource<SupportRequestModel?> {
        val memberId = localSettingsRepository.getMemberId()
        return requestsApiRepository.getRequestById(requestId, memberId)
    }

    //TODO(Add memberId from user auth data)
    suspend fun getMessagesForRequest(requestId: String): Resource<List<MessageModel>> {
        val memberId = localSettingsRepository.getMemberId()
        return requestsApiRepository.getMessagesForRequest(requestId, memberId)
    }

    //TODO(Add memberId from user auth data)
    suspend fun initChatSocket(requestId: String): Resource<Unit> {
        val memberId = localSettingsRepository.getMemberId()
        return requestsApiRepository.initChatSocket(requestId, memberId)
    }

    suspend fun sendMessage(messageText: String): Resource<Unit> {
        return requestsApiRepository.sendMessage(messageText)
    }

    fun observeMessages() = requestsApiRepository.observeMessages().onEach {
        println(it)
    }

    suspend fun disconnect() = requestsApiRepository.closeChatConnection()

}