package com.danotech.rinfo.ui.screens.review

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.Review
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.model.service.ReviewService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ReviewScreenViewModel
@Inject
constructor(
    private val reviewService: ReviewService,
    logService: LogService
) :
    RinfoViewModel(logService) {
    val uiState = mutableStateOf(ReviewUiState())

    init {
        initializer()
    }

    private fun initializer() {
        uiState.value = ReviewUiState()
    }

    val reviewsFlow: Flow<List<Review>> = flow {
        uiState.value = uiState.value.copy(isLoading = true)

        val reviews = reviewService.getReviewsByBusinessId(uiState.value.businessId).first()
        emit(reviews)
    }.onStart {
        uiState.value = uiState.value.copy(isLoading = true)
    }.onCompletion {
        uiState.value = uiState.value.copy(isLoading = false)
    }
}