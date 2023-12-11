package com.example.glazovnetadminapp.domain.repository

import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.entity.supportsDto.SupportRequestDto
import kotlinx.coroutines.flow.Flow

interface RequestsApiRepository {

    suspend fun getAllRequests(apiKey: String): Resource<List<SupportRequestModel>>

    suspend fun addRequest(newRequest: SupportRequestDto): Resource<SupportRequestModel>

    suspend fun initSocket(memberId: String): Resource<Unit>

    fun observerRequests(memberId: String): Flow<SupportRequestModel>

    suspend fun closeConnection()

}