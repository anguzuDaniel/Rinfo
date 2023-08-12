package com.danotech.rinfo.ui.screens.search_business

import com.danotech.rinfo.model.BusinessDocument

/**
 * Represents the state in which the [SearchBusinessScreen] can be
 * @param query the query to search
 * @param isLoading whether the search is loading
 * @param businesses the list of businesses
 * @param error the error message
 * @see SearchBusinessScreen
 * @see SearchBusinessViewModel
 * @see SearchBusinessUi
 * @see SearchBusinessUiState
 */
data class SearchBusinessUiState(
    var query: String = "",
    val isLoading: Boolean = false,
    val businesses: List<BusinessDocument> = emptyList(),
    val error: String = ""
)
