package com.danotech.rinfo.ui.screens.review

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.BusinessDocument
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class ReviewPageViewModel @Inject constructor(
    private val businessAccountService: BusinessAccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(ReviewPageUiState())

    private val currentBusinessId: String
        get() = uiState.value.currentBusiness.id

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

    fun getBusinessById(businessId: String): Flow<BusinessDocument?> {
        return flow {
            // Set isLoading to true
            uiState.value = uiState.value.copy(isLoading = true)

            val business = businessAccountService.getBusinessById(businessId)
            emit(business)
        }.onStart {
            // Set isLoading to true when the flow starts
            uiState.value = uiState.value.copy(isLoading = true)
        }.onCompletion {
            // Set isLoading to false when the flow completes (regardless of success or error)
            uiState.value = uiState.value.copy(isLoading = false)
        }
    }


}