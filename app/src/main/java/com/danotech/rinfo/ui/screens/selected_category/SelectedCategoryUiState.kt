package com.danotech.rinfo.ui.screens.selected_category

import com.danotech.rinfo.model.local.Category

/**
 * This class represents the state of the UI for the [SearchCategoryScreen].
 * @param categories The list of categories to display.
 * @param businesses The list of businesses to display.
 * @param searchedCategory The category that the user is searching for.
 * @param isLoading Whether the UI is loading or not.
 * @param isLoggedIn Whether the user is logged in or not.
 * @see SearchCategoryScreen
 */
data class SelectedCategoryUiState(
    val categories: List<Category> = emptyList(),
    val businesses: List<Business> = emptyList(),
    var searchedCategory: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)