package com.danotech.rinfo.ui.screens.category

import com.danotech.rinfo.model.local.Category

data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val category: String = "",
    var searchInput: String = "",
    val isLoading: Boolean = false,
    val searchedCategory: String = "",
    val isLoggedIn: Boolean = false,
)