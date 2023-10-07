package com.example.glazovnetadminapp.presentation.tariffs

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.tariffs.TariffModel
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

    init {
        loadActiveTariffs()
    }

    fun loadActiveTariffs() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                message = null
            )
            val result = tariffsUseCase.getTariffs(status = true)
            when (result) {
                is Resource.Success -> {
                    state = if (result.data !== null) {
                        state.copy(
                            tariffsData = result.data.toMutableList(),
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
        }
    }

    fun getTariffs(
        status: Boolean? = null
    ): List<TariffModel?> {
        Log.i("TAG", "getTariffs: filtering!")
        return if (status == null) state.tariffsData
        else state.tariffsData.filter {
            it?.isActive == status
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
                //updateUseCase
            }
        }
    }
}