package com.danotech.rinfo.ui.screens.search_categories

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.data.CategoryDao
import com.danotech.rinfo.data.LocalOfflineDatabase
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    private val localOfflineDatabase: LocalOfflineDatabase,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(SearchCategoryUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = SearchCategoryUiState()
    }

    fun onSearchInput(newValue: String) {
        uiState.value = uiState.value.copy(searchedCategory = newValue)
    }

    fun getCategories() =
        localOfflineDatabase.categoryDao().getCategoryByName(uiState.value.searchedCategory)

    fun onSearch(Search: String) {
        flow {
            uiState.value = uiState.value.copy(isLoading = true)
            val categories = localOfflineDatabase.categoryDao().getAllCategories().first()
            emit(categories)

            uiState.value = uiState.value.copy(categories = categories)
        }.onStart {
            uiState.value = uiState.value.copy(isLoading = true)
        }.onCompletion {
            uiState.value = uiState.value.copy(isLoading = false)
        }
    }

    fun onClose() {
        uiState.value = uiState.value.copy(searchedCategory = "")
    }

    fun getAllCategories(): Flow<List<Category>> {
        return flow {
            uiState.value = uiState.value.copy(isLoading = true)

            if (uiState.value.searchedCategory.isEmpty()) {
                val categories = localOfflineDatabase.categoryDao().getAllCategories().first()
                emit(categories)

                uiState.value = uiState.value.copy(categories = categories)
            } else {
                val categories =
                    localOfflineDatabase.categoryDao()
                        .getCategoryByName(uiState.value.searchedCategory)
                        .first()
                emit(categories)

                uiState.value = uiState.value.copy(categories = categories)
            }
        }.onStart {
            uiState.value = uiState.value.copy(isLoading = true)
        }.onCompletion {
            uiState.value = uiState.value.copy(isLoading = false)
        }
    }
}