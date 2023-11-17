package com.example.glazovnetadminapp.presentation.announcements

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.useCases.AnnouncementsUseCase
import com.example.glazovnetadminapp.domain.util.Resource
import com.example.glazovnetadminapp.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class AnnouncementsViewModel @Inject constructor(
    private val announcementsUseCase: AnnouncementsUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(ScreenState<AnnouncementModel>())
    val state = _state.asStateFlow()
    private var _addressesState = MutableStateFlow(ScreenState<AddressFilterElement>())
    val addressesState = _addressesState.asStateFlow()

    private var _announcementToEdit = MutableStateFlow<AnnouncementModel?>(null)
    val announcementToEdit = _announcementToEdit.asStateFlow()

    private var _citiesSearchText = MutableStateFlow("")
    private val citiesSearchJob = _citiesSearchText
        .debounce(500)
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
        .onEach {
            searchForAddresses(
                _citiesSearchText.value,
                _streetsSearchText.value
            )
        }
        .launchIn(viewModelScope)
    private var _streetsSearchText = MutableStateFlow("")
    private val streetsSearchJob = _streetsSearchText
        .debounce(500)
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)
        .onEach {
            searchForAddresses(
                _citiesSearchText.value,
                _streetsSearchText.value
            )
        }
        .launchIn(viewModelScope)

    fun updateSearch(
        citySearch: String,
        streetSearch: String
    ) {
        viewModelScope.launch {
            _citiesSearchText.update { citySearch }
            _streetsSearchText.update { streetSearch }
        }
    }

    private fun searchForAddresses(
        city: String,
        street: String
    ) {
        viewModelScope.launch {
            if (city.isNotBlank() && street.isNotBlank()) {
                _addressesState.update {
                    it.copy(
                        isLoading = true,
                        message = null
                    )
                }
                _addressesState.update {
                    when (val addresses = announcementsUseCase.getAddresses(city, street)) {
                        is Resource.Success -> {
                            it.copy(
                                data = addresses.data!!,
                                isLoading = false,
                                message = addresses.message
                            )
                        }

                        is Resource.Error -> {
                            it.copy(
                                data = emptyList(),
                                isLoading = false,
                                message = addresses.message
                            )
                        }
                    }
                }
            } else _addressesState.update {
                it.copy(
                    data = emptyList(),
                    isLoading = false,
                    message = "not enough arguments"
                )
            }
        }

    }

}