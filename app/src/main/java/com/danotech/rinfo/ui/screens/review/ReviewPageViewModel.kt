package com.danotech.rinfo.ui.screens.review

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewPageViewModel @Inject constructor(
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(ReviewPageUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = ReviewPageUiState()
    }

    fun onReviewPageStart() {
        uiState.value = uiState.value.copy(
            showReviewPage = true
        )
    }

    fun getReview() {
        uiState.value = uiState.value.copy(
            currentReview = LocalReviewProvider.defaultReview,
            showReviewPage = true
        )
    }
}