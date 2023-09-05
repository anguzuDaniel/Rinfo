package com.danotech.rinfo.ui.screens.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.data.LocalOfflineDatabase
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val localOfflineDatabase: LocalOfflineDatabase,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(CategoryUiState())

    val categoriesSearched: StateFlow<CategoryUiState> = localOfflineDatabase
        .categoryDao()
        .getCategoryByName(uiState.value.searchedCategory)
        .filterNotNull()
        .map {
            CategoryUiState(categories = it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CategoryUiState()
        )

    val allCategories: StateFlow<CategoryUiState> = localOfflineDatabase
        .categoryDao()
        .getAllCategories()
        .filterNotNull()
        .map {
            CategoryUiState(categories = it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CategoryUiState()
        )

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = CategoryUiState()
    }

    fun getAllCategories() = localOfflineDatabase.categoryDao().getAllCategories()

    fun getCategoryByName() =
        localOfflineDatabase.categoryDao().getCategoryByName(uiState.value.searchedCategory)

    fun onSearchInput(newValue: String) {
        uiState.value = uiState.value.copy(searchedCategory = newValue)
    }

    fun onSearch(searchQuery: String) {
        uiState.value = uiState.value.copy(searchedCategory = searchQuery)
    }

    fun onClose() {
        uiState.value = uiState.value.copy(searchedCategory = "")
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

