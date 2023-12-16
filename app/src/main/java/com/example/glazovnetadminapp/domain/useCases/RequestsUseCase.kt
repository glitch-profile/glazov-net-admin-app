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
        val apiKey = localSettingsRepository.getSavedApiKey()
        return requestsApiRepository.getAllRequests(apiKey)
    }

    //TODO(Add memberId from user auth data)
    suspend fun initRequestsSocket(): Resource<Unit> {
//        val memberId = localSettingsRepository...
        return requestsApiRepository.initSocket("123456")
    }

    fun observeMessages() = requestsApiRepository.observeRequests()

    suspend fun addRequest(newRequestModel: SupportRequestModel): Resource<SupportRequestModel?> {
        val requestDto = newRequestModel.toSupportRequestDto()
        return requestsApiRepository.addRequest(requestDto)
    }

    suspend fun disconnect() {
        requestsApiRepository.closeConnection()
    }
}