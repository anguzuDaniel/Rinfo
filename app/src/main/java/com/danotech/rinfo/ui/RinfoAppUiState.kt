package com.danotech.rinfo.ui

import com.danotech.rinfo.ui.screens.RInfoScreen

data class RinfoAppUiState(
    val currentScreen: RInfoScreen = RInfoScreen.Start
)
