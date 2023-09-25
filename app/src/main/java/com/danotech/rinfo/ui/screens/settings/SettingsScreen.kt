package com.danotech.rinfo.ui.screens.settings

import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.centeredTopAppBar
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.profile.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SettingsScreen(
    openAndPopUp: (String, String) -> Unit,
    onBackPressed: () -> Unit = {},
    onTabSelected: (RInfoScreen) -> Unit = {},
    onLogoutClicked: () -> Unit = {},
    onNavClicked: (String) -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    window: Window,
) {
    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(viewModel) {
        viewModel.getProfile()
    }

    rememberLazyListState()

    val view = LocalView.current
    val windowInsetsController =
        WindowCompat.getInsetsController(window, view)

    val useDarkIcons = themeViewModel.themeState.value.isDarkMode

    LaunchedEffect(useDarkIcons) {
        windowInsetsController.isAppearanceLightStatusBars = !useDarkIcons
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarDividerColor = Color.White.toArgb()
    }

    Scaffold(
        topBar = {
            centeredTopAppBar(
                onBackClick = {},
                text = R.string.settings,
                hasBack = false
            )
        },
        bottomBar = {
            CenteredBottomBarLayout(
                bottomBar = {
                    RinfoBottomNavigation(
                        currentScreen = RInfoScreen.Settings,
                        onTabSelected = onTabSelected,
                        modifier = Modifier.fillMaxWidth()
                    )
                }, fab = {})
        },
    ) { innerPadding ->
        SettingsContent(
            openAndPopUp = openAndPopUp,
            innerPadding = innerPadding,
            settingType = SettingType.TEXT,
            onLogoutClicked = onLogoutClicked,
            onNavClicked = onNavClicked,
            viewModel = viewModel
        )
    }
}



