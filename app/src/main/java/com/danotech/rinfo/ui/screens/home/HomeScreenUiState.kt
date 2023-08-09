package com.danotech.rinfo.ui.screens.home

import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.search.Business
import java.util.concurrent.Flow

data class HomeScreenUiState(
    val reviews: List<Review> = emptyList(),
    val businesses: List<Business> = emptyList(),
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)