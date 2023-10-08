package com.example.glazovnetadminapp.presentation.tariffs.tariffsList

import android.util.Log
import androidx.annotation.FloatRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
import com.example.glazovnetadminapp.domain.models.tariffs.TariffType
import com.example.glazovnetadminapp.domain.useCases.TariffsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TariffsScreenViewModel @Inject constructor(
    private val tariffsUseCase: TariffsUseCase
): ViewModel() {

    var state by mutableStateOf(TariffsScreenState())
        private set
    var nameFilter by mutableStateOf(
        ""
    ) //TODO("make a working filter")

    init {
        loadActiveTariffs()
    }

    fun loadActiveTariffs() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                message = null
            )
            val result = tariffsUseCase.getTariffs()
            when (result) {
                is Resource.Success -> {
                    state = if (result.data !== null) {
                        state.copy(
                            tariffsData = result.data.filterNotNull().toMutableList(),
                            isLoading = false
                        )

                    } else {
                        state.copy(
                            isLoading = false,
                            message = result.message
                        )
                    }
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
            }
            Log.i("TAG", "loadActiveTariffs: $state")
        }
    }

    fun updateTariff(
        tariff: TariffModel
    ) {
        viewModelScope.launch {
            val tariffIndex = state.tariffsData.indexOfFirst {
                it?.id == tariff.id
            }
            if (tariffIndex == -1) {
                return@launch
            } else {
                state.tariffsData[tariffIndex] = tariff
                tariffsUseCase.updateTariff(tariff = tariff)
            }
        }
    }

    fun removeTariff(
        tariffId: String
    ) {
        viewModelScope.launch {
            val tariffIndex = state.tariffsData.indexOfFirst {
                it?.id == tariffId
            }
            if (tariffIndex == -1) {
                return@launch
            } else {
                state.tariffsData.removeAt(tariffIndex)
                tariffsUseCase.deleteTariff(tariffId = tariffId)
            }
        }
    }

    fun addTariff(
        tariff: TariffModel
    ) {
        viewModelScope.launch {
            state.tariffsData.add(tariff)
            tariffsUseCase.addTariff(tariff = tariff)
        }
    }

    //TODO(Flip api request and local changes in order)
}