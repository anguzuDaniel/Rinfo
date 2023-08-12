package com.danotech.rinfo.ui.screens.search_business

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBusinessViewModel @Inject constructor(
    private val businessAccountService: BusinessAccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(SearchBusinessUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = SearchBusinessUiState()
    }

    fun onClose() {
        uiState.value = uiState.value.copy(query = "")
    }

    fun onSearchInput() {
        viewModelScope.launch {
            val businesses =
                businessAccountService.getBusinessWhereLike(uiState.value.query).first()
            uiState.value = uiState.value.copy(isLoading = false, businesses = businesses)
        }
    }

    fun onQueryChanged(newValue: String) {
        uiState.value = uiState.value.copy(query = newValue)
    }

    fun onSearch(query: String) {
        uiState.value = uiState.value.copy(isLoading = true, query = query)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}