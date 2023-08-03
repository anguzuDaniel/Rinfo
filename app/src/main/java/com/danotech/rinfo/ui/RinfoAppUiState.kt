package com.danotech.rinfo.ui

import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.RInfoScreen

data class RinfoAppUiState(
    var currentScreen: RInfoScreen = RInfoScreen.Home,
    val isShowingBottomBar: Boolean = true,
    var currentReview: Review = LocalReviewProvider.defaultReview
)
