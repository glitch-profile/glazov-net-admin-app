package com.example.glazovnetadminapp.presentation.announcements

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.glazovnetadminapp.domain.models.announcements.AddressFilterElement
import com.example.glazovnetadminapp.domain.models.announcements.AnnouncementModel
import com.example.glazovnetadminapp.domain.useCases.AnnouncementsUseCase
import com.example.glazovnetadminapp.entity.AddressModelDto
import com.example.glazovnetadminapp.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class AnnouncementsViewModel @Inject constructor(
    private val announcementsUseCase: AnnouncementsUseCase
): ViewModel() {

    private var _state = MutableStateFlow(ScreenState<AnnouncementModel>())
    val state = _state.asStateFlow()

    private var _announcementToEdit = MutableStateFlow<AnnouncementModel?>(null)
    val announcementToEdit = _announcementToEdit.asStateFlow()

    private var _citiesSearchText = MutableStateFlow("")
    val citiesSearchText = _citiesSearchText.asStateFlow()
    private var _streetsSearchText = MutableStateFlow("")
    val streetsSearchText = _streetsSearchText.asStateFlow()

    private val _filteredAddresses = combine(_citiesSearchText, _streetsSearchText) {cityText, streetText ->
        Log.i("TAG", "Search should go now with params: $cityText, $streetText")
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly, //TODO:Change to WhileSubscribed
        emptyList<AddressModelDto>() //TODO:Add Model Element
    )


    init {
        _filteredAddresses
            .debounce(500)
            .distinctUntilChanged()
            .onEach {
                Log.i("TAG", "Search should go now!: ")
            }
            .launchIn(viewModelScope)
    }

    fun updateSearch(
        citySearch: String,
        streetSearch: String
    ) {
        viewModelScope.launch {
            _citiesSearchText.update { citySearch }
            _streetsSearchText.update { streetSearch }
        }
    }

}