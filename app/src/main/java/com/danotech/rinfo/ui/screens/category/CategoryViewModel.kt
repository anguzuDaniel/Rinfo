package com.danotech.rinfo.ui.screens.category

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.data.LocalOfflineDatabase
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val localOfflineDatabase: LocalOfflineDatabase,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(CategoryUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = CategoryUiState()
    }

    fun onAddCategoryClick() {
        launchCatching {
            val category = Category(
                name = "Hotels",
            )

            localOfflineDatabase.categoryDao().insertCategory(category)
        }.invokeOnCompletion {
            SnackbarManager.showMessage(R.string.category_added_successfully)
        }
    }
}