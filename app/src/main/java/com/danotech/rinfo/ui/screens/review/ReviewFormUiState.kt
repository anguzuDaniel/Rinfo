package com.danotech.rinfo.ui.screens.review

data class ReviewFormUiState(
    val reviewedBusinessId: String = "",
    val reviewerUserId: String = "",
    val rating: Int = 0,
    val title: String = "",
    val review: String = "",
    val isPositive: Boolean = false,
    var submitButtonEnabled: Boolean = false,
    val date: String = ""
)