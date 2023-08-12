package com.danotech.rinfo.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.RinfoViewModel
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.components.Review
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/***
 *
 *
 */
@HiltViewModel
class HomesScreenViewModel @Inject constructor(
    private val businessAccountService: BusinessAccountService,
    private val accountService: AccountService,
    logService: LogService,
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(HomeScreenUiState())

    val businessFlow: Flow<List<Business>> = flow {
        uiState.value = uiState.value.copy(isLoading = true)

        val businesses = businessAccountService.getAllBusiness(5).first()
        emit(businesses)
    }.onStart {
        uiState.value = uiState.value.copy(isLoading = true)
    }.onCompletion {
        uiState.value = uiState.value.copy(isLoading = false)
    }

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

    fun isLoggedIn(): Boolean {
        return true
    }

    fun getBusinesses() {
        uiState.value = uiState.value.copy(isLoading = true)

        launchCatching {
            val businesses = businessAccountService.getAllBusiness(5).first()

//            uiState.value = uiState.value.copy(
//                businesses = businesses
//            )
        }.invokeOnCompletion {
            uiState.value = uiState.value.copy(isLoading = false)
        }
    }
}