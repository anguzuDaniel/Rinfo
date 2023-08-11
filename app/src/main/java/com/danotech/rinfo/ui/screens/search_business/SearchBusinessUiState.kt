package com.danotech.rinfo.ui.screens.search_business

import com.danotech.rinfo.model.BusinessDocument

/**
 * Represents the state in which the [SearchBusinessScreen] can be
 */
data class SearchBusinessUiState(
    var query: String = "",
    val isLoading: Boolean = false,
    val businesses: List<BusinessDocument> = emptyList(),
    val error: String = ""
)
