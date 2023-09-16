package com.danotech.rinfo.ui.screens.settings

import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.NewLabel
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.BuildConfig
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.centeredTopAppBar
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import java.time.Year

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SettingsViewModel,
    innerPadding: PaddingValues,
    settingType: SettingType,
    onLogoutClicked: () -> Unit,
    onNavClicked: (String) -> Unit = {},
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val themeState by themeViewModel.themeState.collectAsState()

    val scrollState = rememberLazyListState()
//    val uiState = viewModel.uiState.collectAsState().value
    16.dp

    Surface(
        modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection()),
    ) {
        LazyColumn(
            modifier = modifier
                .height(LocalConfiguration.current.screenHeightDp.dp)
                .padding(bottom = 40.dp),
            contentPadding = PaddingValues(top = innerPadding.calculateTopPadding()),
            state = scrollState,
        ) {
            item {
                SettingSectionHeading(
                    text = R.string.appearance,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = if (themeState.isDarkMode) Icons.Filled.ModeNight else Icons.Filled.LightMode,
                    description = if (themeState.isDarkMode) "ON" else "OFF",
                    iconDesc = R.string.dark_mode,
                    name = R.string.dark_mode,
                    settingType = SettingType.SWITCH,
                    isSwitchedOn = themeState.isDarkMode
                ) {
                    themeState.isDarkMode = it
                    themeViewModel.toggleTheme()
                }
            }

            item {
                SettingSectionHeading(
                    text = R.string.notifications,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Icons.Filled.Notifications,
                    iconDesc = R.string.notifications,
                    name = R.string.notifications,
                    onClick = {},
                )
            }

            item {
                SettingSectionHeading(
                    text = R.string.account,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Icons.Filled.AccountBox,
                    description = "See and manage all information your account, delete or deactivate your account.",
                    iconDesc = R.string.account,
                    name = R.string.account,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.Account.name)
                    }
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Icons.Filled.AccountBox,
                    description = "Privacy policy, Terms of use, about app..",
                    iconDesc = R.string.about,
                    name = R.string.about,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.About.name)
                    }
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Icons.Filled.Feedback,
                    iconDesc = R.string.send_feed_back,
                    description = "Send us feedback on what we can do better and how we can make your experience better.",
                    name = R.string.send_feed_back,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.FeedBack.name)
                    }
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Icons.Filled.NewLabel,
                    iconDesc = R.string.whats_new,
                    description = "See the latest features... feature and updated..",
                    name = R.string.whats_new,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.WhatsNew.name)
                    }
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Icons.AutoMirrored.Filled.ContactSupport,
                    iconDesc = R.string.contact_us,
                    name = R.string.contact_us,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.ContactUs.name)
                    }
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Icons.AutoMirrored.Filled.Logout,
                    iconDesc = R.string.logout,
                    name = R.string.logout,
                    settingType = SettingType.BUTTON,
                    onClick = {
                        viewModel.onLogoutClick(openAndPopUp)
                        onLogoutClicked()
                    },
                    opensDialogWhenClicked = true
                )
            }

            item {
                val version = "Version number: ${BuildConfig.VERSION_NAME} Beta"

                val year = Year.now()

                AppVersion(
                    versionText = version,
                    copyrights = "© $year codevation",
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun SettingSectionHeading(
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
        )
    }
}