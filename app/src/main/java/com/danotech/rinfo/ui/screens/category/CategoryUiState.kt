package com.danotech.rinfo.ui.screens.category

import com.danotech.rinfo.model.local.Category

/**
 * This class represents the state of the UI for the [CategoryScreen].
 * @param categories The list of categories to display.
 * @param category The category to display.
 * @param searchInput The search input.
 * @param isLoading Whether the screen is loading.
 * @param searchedCategory The searched category.
 * @param isLoggedIn Whether the user is logged in.
 * @see CategoryScreen
 * @see CategoryViewModel
 */
data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val category: String = "",
    var searchInput: String = "",
    val isLoading: Boolean = false,
    var searchedCategory: String = "",
    val isLoggedIn: Boolean = false,
)