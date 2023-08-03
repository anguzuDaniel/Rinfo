package com.danotech.rinfo

import androidx.lifecycle.ViewModel
import com.danotech.rinfo.ui.RinfoAppUiState
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.RInfoScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RinfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RinfoAppUiState())
    val uiState: StateFlow<RinfoAppUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value = RinfoAppUiState()
    }

    fun onScreenSelected(screen: RInfoScreen) {
        when (screen) {
            RInfoScreen.Home -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Home
                    )
                }
            }

            RInfoScreen.Review -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Review
                    )
                }
            }

            RInfoScreen.Category -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Category,
                        isShowingBottomBar = false
                    )
                }
            }

            RInfoScreen.Login -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Login
                    )
                }
            }

            RInfoScreen.Notification -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Notification
                    )
                }
            }

            RInfoScreen.Settings -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Settings
                    )
                }
            }

            RInfoScreen.Register -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Register
                    )
                }
            }

            RInfoScreen.Search -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Search
                    )
                }
            }

            RInfoScreen.Favourites -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Favourites
                    )
                }
            }

            else -> {
                _uiState.update {
                    it.copy(
                        currentScreen = RInfoScreen.Home
                    )
                }
            }
        }
    }

    fun popBackStack() {
        _uiState.update {
            it.copy(
                currentScreen = RInfoScreen.Home
            )
        }
    }

    fun showBusinessDetails(review: Review) {
        _uiState.update {
            it.copy(
                currentReview = review,
                currentScreen = RInfoScreen.Review
            )
        }
    }
}