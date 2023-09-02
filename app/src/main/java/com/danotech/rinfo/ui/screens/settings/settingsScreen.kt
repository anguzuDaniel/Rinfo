package com.danotech.rinfo.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.SettingSwitch
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

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

    Scaffold(
        topBar = {
            RinfoTopAppBar(
                title = stringResource(id = R.string.settings),
                isShowingHomePage = false,
                onBackButtonClicked = onBackPressed,
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
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    settingType: SettingType,
    onLogoutClicked: () -> Unit,
    onNavClicked: (String) -> Unit = {},
    viewModel: SettingsViewModel,
) {
    val uiState = viewModel.uiState.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding,
    ) {
        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.Nightlight,
                name = R.string.dark_mode,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.dark_mode,
                onClick = {

                }
            )
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.Notifications,
                name = R.string.notifications,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.notifications,
            ) {
                // here you can do anything - navigate - open other settings, ...

            }
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
                }
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
                leadingIcon = Icons.Filled.ContactSupport,
                name = R.string.contact_us,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.contact_us,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.About.name)
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
                    settingsViewModel.onLogoutClick(openAndPopUp)
                    onLogoutClicked()
                }
            )
        }
    }
}


@Composable
fun SettingsClickableComp(
    leadingIcon: ImageVector,
    icon: ImageVector,
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    settingType: SettingType = SettingType.SWITCH,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = 2.dp,
                horizontal = dimensionResource(id = R.dimen.setting_card_padding)
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(id = name),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(3f),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1.0f))
                if (settingType == SettingType.SWITCH) {
                    SettingSwitch(
                        switchOn = false,
                        onSwitchChanged = {},
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Icon(
                        Icons.Rounded.KeyboardArrowRight,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(id = R.string.arrow_forward),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}