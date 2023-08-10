package com.danotech.rinfo.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(SearchCategoryUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = SearchCategoryUiState()
    }
}