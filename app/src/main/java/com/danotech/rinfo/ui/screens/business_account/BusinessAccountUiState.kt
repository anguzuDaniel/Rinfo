package com.danotech.rinfo.ui.screens.business_account

import com.danotech.rinfo.model.local.Category

data class BusinessAccountUiState(
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val whatsapp: String = "",
    val businessCategory: Category = Category(name = ""),
    val reviews: Int = 0,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)
