package com.danotech.rinfo.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.NewLabel
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.BuildConfig
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.SearchTextField
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    openAndPopUp: (String, String) -> Unit,
    onBackPressed: () -> Unit = {},
    onTabSelected: (RInfoScreen) -> Unit = {},
    onLogoutClicked: () -> Unit = {},
    onNavClicked: (String) -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    BackHandler {
        onBackPressed()
    }

    LaunchedEffect(viewModel) {
        viewModel.getProfile()
    }

    val listState = rememberLazyListState()
    val hasScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 0
        }
    }
    val appBarElevation by animateDpAsState(targetValue = if (hasScrolled) 4.dp else 0.dp)


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (isSystemInDarkTheme()) {
                        MaterialTheme.colorScheme.surfaceVariant.copy(
                            alpha = if (hasScrolled) 1f else 0f
                        )
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                ),
                modifier = Modifier.shadow(appBarElevation),
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = { },
            )
        },
        bottomBar = {
            CenteredBottomBarLayout(bottomBar = {
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
    val paragraphSpace = 16.dp

    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding,
        state = scrollState,
        userScrollEnabled = false
    ) {
        item {
            SearchTextField(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(paragraphSpace))
        }

        item {
            SettingsClickableComp(
                leadingIcon = if (themeState.isDarkMode) Icons.Filled.LightMode else Icons.Filled.ModeNight,
                name = R.string.dark_mode,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.dark_mode,
                onSwitchClick = {
                    themeState.isDarkMode = it
                    themeViewModel.toggleTheme()
                },
                settingType = SettingType.SWITCH,
                isSwitchedOn = themeState.isDarkMode,
                description = if (themeState.isDarkMode) "ON" else "OFF"
            )
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
                name = R.string.notifications,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.notifications,
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
                name = R.string.account,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.account,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.Account.name)
                },
                description = ""
            )
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.AccountBox,
                name = R.string.about,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.about,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.About.name)
                }
            )
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.Feedback,
                name = R.string.send_feed_back,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.send_feed_back,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.FeedBack.name)
                }
            )
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.NewLabel,
                name = R.string.whats_new,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.whats_new,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.WhatsNew.name)
                }
            )
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.ContactSupport,
                name = R.string.contact_us,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.contact_us,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.ContactUs.name)
                }
            )
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.Logout,
                name = R.string.logout,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.logout,
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

            AppVersion(
                versionText = version,
                copyrights = "© 2023 codevation"
            ) {

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
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
        )
    }
}