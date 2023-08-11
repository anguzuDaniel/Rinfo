package com.danotech.rinfo.ui.screens.search_categories

import com.danotech.rinfo.model.local.Category

data class SearchCategoryUiState(
    val categories: List<Category> = emptyList(),
    val businesses: List<Business> = emptyList(),
    val searchedCategory: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)