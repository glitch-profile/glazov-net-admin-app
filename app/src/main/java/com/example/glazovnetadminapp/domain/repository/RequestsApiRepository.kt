package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.support.MessageModel
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.supportsDto.MessageModelDto
import com.example.glazovnetadminapp.entity.supportsDto.SupportRequestDto
import kotlinx.coroutines.flow.Flow

interface RequestsApiRepository {

    suspend fun getAllRequests(apiKey: String): Resource<List<SupportRequestModel>>

    suspend fun getRequestById(requestId: String, memberId: String): Resource<SupportRequestModel?>

    suspend fun addRequest(newRequest: SupportRequestDto): Resource<SupportRequestModel?>

    suspend fun initRequestsSocket(memberId: String): Resource<Unit>

    fun observeRequests(): Flow<SupportRequestModel>

    suspend fun initChatSocket(requestId: String, memberId: String): Resource<Unit>

    suspend fun sendMessage(message: String): Resource<Unit>

    fun observeMessages(): Flow<MessageModel>

    suspend fun closeRequestsConnection()

    suspend fun closeChatConnection()

}