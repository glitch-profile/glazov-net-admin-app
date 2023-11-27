package com.example.glazovnetadminapp.presentation.announcements

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

    private val _state = MutableStateFlow(ScreenState<AnnouncementModel>())
    val state = _state.asStateFlow()
    private val _addressesState = MutableStateFlow(ScreenState<AddressFilterElement>())
    val addressesState = _addressesState.asStateFlow()
    private val _selectedAddresses = MutableStateFlow<List<AddressFilterElement>>(emptyList())
    val selectedAddresses = _selectedAddresses.asStateFlow()

    private val _announcementToEdit = MutableStateFlow(ScreenState<AnnouncementModel>())
    val announcementToEdit = _announcementToEdit.asStateFlow()

    private val _citiesSearchText = MutableStateFlow("")
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
    private val _citiesList = MutableStateFlow(ScreenState<String>())
    val citiesList = _citiesList.asStateFlow()

    private val _streetsSearchText = MutableStateFlow("")
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

    init {
        getAllAnnouncements()
        loadCitiesList()
    }


    //announcementScreen staff
    fun getAllAnnouncements() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            val result = announcementsUseCase.getAnnouncements()
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        if (result.data!!.isNotEmpty()) {
                            it.copy(
                                data = result.data,
                                isLoading = false
                            )
                        } else {
                            it.copy(
                                isLoading = false,
                                message = result.message
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = result.message
                        )
                    }
                }
            }
        }
    }

    fun deleteAnnouncement(
        announcement: AnnouncementModel
    ) {
        viewModelScope.launch {
            val result = announcementsUseCase.deleteAnnouncement(announcement.id)
            if (result.data == true) {
                val announcementsList = state.value.data.toMutableList()
                if (announcementsList.remove(announcement)) {
                    _state.update {
                        it.copy(data = announcementsList)
                    }
                }
            } else {
                _state.update {
                    it.copy(
                        message = result.message
                    )
                }
            }
        }
    }

    //AddAnnouncement staff below
    fun updateSearch(
        citySearch: String,
        streetSearch: String
    ) {
        viewModelScope.launch {
            _citiesSearchText.update { citySearch }
            _streetsSearchText.update { streetSearch }
        }
    }

    private fun loadCitiesList() {
        viewModelScope.launch {
            _citiesList.update {
                it.copy(
                    isLoading = true,
                    message = null
                )
            }
            val result = announcementsUseCase.getCitiesList("")
            _citiesList.update {
                when (result) {
                    is Resource.Success -> {
                        it.copy(
                            data = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        it.copy(
                            isLoading = false,
                            message = "Error"
                        )
                    }
                }
            }
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
                                data = addresses.data!!.map { checkIfAddressAlreadySelected(it) },
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
                    data = selectedAddresses.value.reversed(),
                    isLoading = false,
                    message = "showing selected addresses"
                )
            }
        }
    }

    private fun checkIfAddressAlreadySelected(
        address: AddressFilterElement
    ): AddressFilterElement {
        val isAddressSelected = selectedAddresses.value.any { selectedAddress ->
            selectedAddress.city == address.city
                    && selectedAddress.street == address.street
                    && selectedAddress.houseNumber == address.houseNumber
        }
        return if (isAddressSelected) {
            address.copy(isSelected = true)
        } else address
    }

    fun changeSelectionOfAddressElement(
        addressFilterElement: AddressFilterElement
    ) {
        val addressesList = addressesState.value.data.toMutableList()
        val addressIndex = addressesList.indexOfFirst { it == addressFilterElement }
        if (addressIndex != -1) {
            addressesList[addressIndex] = addressFilterElement.copy(
                isSelected = !addressFilterElement.isSelected
            )
            _addressesState.update { state ->
                state.copy(
                    data = addressesList
                )
            }
        }

        val selectedAddresses = selectedAddresses.value.toMutableList()
        if (addressFilterElement.isSelected) {
            selectedAddresses.remove(addressFilterElement)
        } else {
            selectedAddresses.add(
                addressFilterElement.copy(
                    isSelected = true
                )
            )
        }
        _selectedAddresses.update {
            selectedAddresses
        }
    }

    fun createAnnouncement(
        title: String,
        text: String,
    ) {
        viewModelScope.launch {
            _announcementToEdit.update {
                it.copy(
                    isLoading = true,
                    message = "getting announcement"
                )
            }
            val announcement = AnnouncementModel(
                id = "",
                title = title,
                text = text,
                filters = _selectedAddresses.value,
                creationDate = null
            )
            _announcementToEdit.update {
                it.copy(
                    message = "uploading announcement"
                )
            }
            val result = announcementsUseCase.createAnnouncement(announcement)
            if (result.data != null) {
                val announcementsList = _state.value.data.toMutableList()
                announcementsList.add(
                    index = 0,
                    element = result.data
                )
                _state.update {
                    it.copy(
                        data = announcementsList
                    )
                }
                _announcementToEdit.update {
                    it.copy(
                        isLoading = false,
                        message = "completed"
                    )
                }
            } else {
                _announcementToEdit.update {
                    it.copy(
                        isLoading = false,
                        message = result.message ?: "unknown error"
                    )
                }
            }
        }
    }

    fun clearSelectedAddresses() {
        viewModelScope.launch{
            _selectedAddresses.update { emptyList() }
            val currentAddressesList = _addressesState.value.data
            val newAddressesList = currentAddressesList.map { it.copy(isSelected = false) }
            _addressesState.update {
                it.copy( data = newAddressesList )
            }
        }
    }
}