package com.danotech.rinfo.ui.screens.search

data class SearchCategoryUiState(
    val businesses: List<Business> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)