package com.danotech.rinfo.ui.screens.review

import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.BusinessDocument
import com.danotech.rinfo.ui.components.Review

data class ReviewPageUiState(
    val currentReview: Review = LocalReviewProvider.defaultReview,
    val currentBusiness: BusinessDocument = BusinessDocument(),
    val currentBusinessId: String = "",
    val showReviewPage: Boolean = false,
    val isLoading: Boolean = false,
)