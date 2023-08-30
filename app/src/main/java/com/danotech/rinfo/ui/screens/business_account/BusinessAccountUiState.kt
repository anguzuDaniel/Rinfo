package com.danotech.rinfo.ui.screens.business_account

import android.graphics.Bitmap
import com.danotech.rinfo.model.local.Category

data class BusinessAccountUiState(
    val logo: String = "",
    val profilePicture: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val whatsapp: String = "",
    var businessCategory: Category = Category(name = ""),
    val businessCategoryList: List<Category> = emptyList(),
    val reviews: Int = 0,
    val isLoading: Boolean = false,
    val imageLoading: Boolean = false,
    var showBottomSheet: Boolean = false,
    val isLoggedIn: Boolean = false,
)
