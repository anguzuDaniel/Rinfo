package com.danotech.rinfo.ui.screens.settings

import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.BuildConfig
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.Rinfo
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.SectionHeading
import com.danotech.rinfo.ui.components.centeredTopAppBar
import com.danotech.rinfo.ui.components.rinfo.Contactus
import com.danotech.rinfo.ui.components.rinfo.Dark
import com.danotech.rinfo.ui.components.rinfo.Feed
import com.danotech.rinfo.ui.components.rinfo.Feedback
import com.danotech.rinfo.ui.components.rinfo.Light
import com.danotech.rinfo.ui.components.rinfo.Logoout
import com.danotech.rinfo.ui.components.rinfo.Notificationon
import com.danotech.rinfo.ui.components.rinfo.Whatsnew
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.profile.ProfileViewModel
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
                SectionHeading(
                    text = R.string.account,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }

            item {
                SettingsClickableComp(
                    hasImage = true,
                    imageUrl = viewModel.uiState.value.profileImageUrl,
                    leadingIcon = Icons.Filled.AccountBox,
                    description = "Update, download, delete or deactivate your account.",
                    iconDesc = R.string.account,
                    name = R.string.account,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.Account.name)
                    }
                )
            }

            item {
                SectionDivider()
            }

            item {
                SectionHeading(
                    text = R.string.appearance,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = if (themeState.isDarkMode) Rinfo.Dark else Rinfo.Light,
                    description = if (themeState.isDarkMode) "On" else "Off",
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
                SectionDivider()
            }

            item {
                SectionHeading(
                    text = R.string.notifications,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Rinfo.Notificationon,
                    iconDesc = R.string.notifications,
                    name = R.string.notifications,
                    onClick = {},
                )
            }

            item {
                SectionDivider()
            }

            item {
                SectionHeading(
                    text = R.string.others,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Rinfo.Feed,
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
                    leadingIcon = Rinfo.Feedback,
                    iconDesc = R.string.send_feed_back,
                    description = "We would like to be here from you.",
                    name = R.string.send_feed_back,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.FeedBack.name)
                    }
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Rinfo.Whatsnew,
                    iconDesc = R.string.whats_new,
                    description = "See the latest features & feature updates.",
                    name = R.string.whats_new,
                    settingType = settingType,
                    onClick = {
                        onNavClicked(RInfoScreen.WhatsNew.name)
                    }
                )
            }

            item {
                SettingsClickableComp(
                    leadingIcon = Rinfo.Contactus,
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
                    leadingIcon = Rinfo.Logoout,
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

/**
 * Section divider
 * shows where section starts and ends
 */
@Composable
private fun SectionDivider() {
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider()
    Spacer(modifier = Modifier.height(20.dp))
}

