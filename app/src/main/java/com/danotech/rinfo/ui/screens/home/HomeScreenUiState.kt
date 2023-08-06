package com.danotech.rinfo.ui.screens.home

import com.danotech.rinfo.ui.components.Review

data class HomeScreenUiState(
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)