package com.danotech.rinfo.ui.screens.review

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.R
import com.danotech.rinfo.common.snackbar.SnackbarManager
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.danotech.rinfo.ui.screens.business.bottomSheet.BottomSheetUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewFormViewModel @Inject constructor(
    private val reviewService: ReviewService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(ReviewFormUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = ReviewFormUiState()
    }

    fun onTitleInput(title: String) {
        uiState.value = uiState.value.copy(title = title)
    }

    fun onReviewInput(review: String) {
        uiState.value = uiState.value.copy(review = review)
    }

    fun onDateInput(date: String) {
        uiState.value = uiState.value.copy(date = date)
    }

    fun addBusinessIdInformation(reviewedBusinessId: String, reviewerUserId: String) {
        uiState.value = uiState.value.copy(
            reviewedBusinessId = reviewedBusinessId,
            reviewerUserId = reviewerUserId
        )
    }

    fun onRatingChanged(rating: Int) {
        uiState.value = uiState.value.copy(
            rating = rating
        )
    }

    fun addReview() {
        if (uiState.value.title.isEmpty()) {
            SnackbarManager.showMessage(R.string.review_title_empty)
            return
        }

        if (uiState.value.review.isEmpty()) {
            SnackbarManager.showMessage(R.string.review_empty)
            return
        }

        uiState.value = uiState.value.copy(
            isPositive = uiState.value.rating > 3,
        )

        launchCatching {
            reviewService.create(
                Review(
                    reviewedBusinessId = uiState.value.reviewedBusinessId,
                    reviewerUserId = uiState.value.reviewerUserId,
                    title = uiState.value.title,
                    rating = uiState.value.rating,
                    review = uiState.value.review,
                    postive = uiState.value.isPositive,
                    date = uiState.value.date
                )
            )
        }.invokeOnCompletion {
            SnackbarManager.showMessage(R.string.review_added)
        }
    }
}