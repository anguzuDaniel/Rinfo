package com.danotech.rinfo.ui.screens.review

import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.ui.components.Review

data class ReviewPageUiState(
    val currentReview: Review = LocalReviewProvider.defaultReview,
    val showReviewPage: Boolean = false
)