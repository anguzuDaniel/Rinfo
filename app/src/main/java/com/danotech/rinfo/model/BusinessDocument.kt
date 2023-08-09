package com.danotech.rinfo.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.category.Category
import com.google.firebase.firestore.DocumentId

data class BusinessDocument(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val phone: String = "",
    val email: String = "",
    val businessCategory: String = Category("", Icons.Default.Business, 0).name,
    val reviews: Int = 0,
)
