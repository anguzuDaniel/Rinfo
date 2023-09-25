package com.danotech.rinfo.ui.screens.review

data class ReviewFormUiState(
    val reviewedBusinessId: String = "",
    val reviewerUserId: String = "",
    val rating: Int = 0,
    val title: String = "",
    val name: String = "",
    val review: String = "",
    val message: String = "",
    val profileUrl: String = "",
    val isPositive: Boolean = false,
    var submitButtonEnabled: Boolean = false,
    val date: String = "",
    var isLoading: Boolean = false
)