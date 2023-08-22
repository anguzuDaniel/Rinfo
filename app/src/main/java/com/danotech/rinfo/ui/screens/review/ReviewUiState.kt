package com.danotech.rinfo.ui.screens.review

import com.danotech.rinfo.model.Review

/**
 *  Represents the UI of the Review
 */
data class ReviewUiState(
    val businessId: String = "",
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)
