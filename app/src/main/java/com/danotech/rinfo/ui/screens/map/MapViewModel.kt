package com.danotech.rinfo.ui.screens.map

import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.android.gms.maps.model.MapStyleOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val businessAccountService: BusinessAccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState = _uiState.asStateFlow()

    fun onClose() {
        _uiState.value = _uiState.value.copy(query = "")
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.toggleFalloutMap -> {
                _uiState.value = _uiState.value.copy(
                    properties = _uiState.value.properties.copy(
                        mapStyleOptions = if (_uiState.value.isFalloutMap) {
                            null
                        } else {
                            MapStyleOptions(MapStyle.json)
                        }
                    ),
                    isFalloutMap = !_uiState.value.isFalloutMap
                )
            }
        }
    }

    fun onSearchInput() {
        viewModelScope.launch {
            val businesses =
                businessAccountService.getBusinessWhereLike(_uiState.value.query).first()
            _uiState.value = _uiState.value.copy(isLoading = false, businesses = businesses)
        }
    }

    fun onQueryChanged(newValue: String) {
        _uiState.value = _uiState.value.copy(query = newValue)
    }

    fun onSearch(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
    }

    fun searchBusinesses() {
        launchCatching {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val businesses =
                businessAccountService.getBusinessWhereLike(_uiState.value.query).first()
            _uiState.value = _uiState.value.copy(businesses = businesses)
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}