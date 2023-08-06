package com.danotech.rinfo.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.components.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/***
 *
 *
 */
@HiltViewModel
class HomesScreenViewModel @Inject constructor(
    logService: LogService,
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(HomeScreenUiState())

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        uiState.value = HomeScreenUiState()
    }

    fun showReviews(): List<Review> {
        uiState.value = uiState.value.copy(
            reviews = LocalReviewProvider.reviews
        )
        return uiState.value.reviews
    }
}