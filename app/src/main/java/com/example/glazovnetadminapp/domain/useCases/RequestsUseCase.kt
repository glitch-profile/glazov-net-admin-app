package com.example.glazovnetadminapp.domain.useCases

import com.example.glazovnetadminapp.data.mappers.toSupportRequestDto
import com.example.glazovnetadminapp.domain.models.support.SupportRequestModel
import com.example.glazovnetadminapp.domain.repository.LocalSettingsRepository
import com.example.glazovnetadminapp.domain.repository.RequestsApiRepository
import com.example.glazovnetadminapp.domain.util.Resource
import javax.inject.Inject

class RequestsUseCase @Inject constructor(
    private val localSettingsRepository: LocalSettingsRepository,
    private val requestsApiRepository: RequestsApiRepository
) {

    suspend fun getAllRequests(): Resource<List<SupportRequestModel>> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return requestsApiRepository.getAllRequests(token)
    }

    suspend fun initRequestsSocket(): Resource<Unit> {
        val token = localSettingsRepository.getLoginToken() ?: ""
        return requestsApiRepository.initRequestsSocket(token)
    }

    fun observeRequests() = requestsApiRepository.observeRequests()

    suspend fun addRequest(newRequestModel: SupportRequestModel): Resource<SupportRequestModel?> {
        val requestDto = newRequestModel.toSupportRequestDto()
        val token = localSettingsRepository.getLoginToken() ?: ""
        return requestsApiRepository.addRequest(requestDto, token)
    }

    suspend fun disconnect() {
        requestsApiRepository.closeRequestsConnection()
    }
}