package com.danotech.rinfo.ui.screens.business_account

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.category.Category

data class BusinessAccountUiState(
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val businessCategory: Category = Category("", Icons.Default.Business, 0),
    val reviews: Int = 0,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)
