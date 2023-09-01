package com.danotech.rinfo.ui.screens.business

import android.graphics.Bitmap
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review

/**
 * This class represents the state of the UI for the [ReviewPageScreen].
 * @param currentReview The current review to display.
 * @param currentBusiness The current business to display.
 * @param currentUserId The current user id to display.
 * @param currentBusinessReviews The current business reviews to display.
 * @param currentBusinessId The current business id to display.
 * @param showReviewPage Whether the review page is shown or not.
 * @param isLoading Whether the UI is loading or not.
 * @param reviewInput The review input to display.
 * @see ReviewPageScreen
 */
data class BusinessUiState(
    val currentReview: List<Review> = emptyList(),
    val currentBusiness: Business = Business(),
    val currentUserId: String = "",
    val currentBusinessReviews: List<Review> = emptyList(),
    var currentBusinessImages: List<Bitmap> = emptyList(),
    val currentBusinessId: String = "",
    val showReviewPage: Boolean = false,
    val isLoading: Boolean = false,
    var reviewInput: String = "",
    var imagesLoading: Boolean = false
)