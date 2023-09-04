package com.danotech.rinfo.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NewLabel
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowRight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.SearchTextField
import com.danotech.rinfo.ui.components.SettingSwitch
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
                    IconButton(onClick = { /*TODO*/ }) {
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
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    settingType: SettingType,
    onLogoutClicked: () -> Unit,
    onNavClicked: (String) -> Unit = {},
    viewModel: SettingsViewModel,
) {
    val uiState = viewModel.uiState.collectAsState().value
    val paragraphSpace = 16.dp

    LazyColumn(
        modifier = modifier,
        contentPadding = innerPadding,
    ) {
        item {
            SearchTextField()
            Spacer(modifier = Modifier.height(paragraphSpace))
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.Nightlight,
                name = R.string.dark_mode,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.dark_mode,
                onClick = {}
            )
        }

        item {
            SettingsClickableComp(
                leadingIcon = Icons.Filled.Notifications,
                name = R.string.notifications,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.notifications,
                onClick = {}
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
                leadingIcon = Icons.Filled.Feedback,
                name = R.string.send_feed_back,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.send_feed_back,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.About.name)
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
                },
                opensDialogWhenClicked = true
            )
        }

        item {
            AppVersion(
                versionText = "1.0.0",
                copyrights = "© 2023 DanoTech"
            ) {

            }
        }
    }
}

/**
 * Clickable setting subsection redirector and action
 * used to redirect to particular page
 * or used for particular page
 * @param leadingIcon leading icon shown before the text/name
 * @param icon
 * @param iconDesc the icon description
 * @param name text shown for the setting
 * @param settingType
 * @param onClick call back function, called when clicked
 * @param opensDialogWhenClicked if true the end icon is not shown
 */
@Composable
fun SettingsClickableComp(
    leadingIcon: ImageVector,
    icon: ImageVector,
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    settingType: SettingType = SettingType.SWITCH,
    onClick: () -> Unit,
    opensDialogWhenClicked: Boolean = false
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.weight(1f),
                tint = if (!opensDialogWhenClicked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
            )
            Text(
                text = stringResource(id = name),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (!opensDialogWhenClicked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.weight(
                    if (!opensDialogWhenClicked) 3f else 4f
                ),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1.0f))
            if (settingType == SettingType.SWITCH) {
                SettingSwitch(
                    clicked = false,
                    onSwitchChanged = {},
                    modifier = Modifier.weight(1f)
                )
            } else {
                if (!opensDialogWhenClicked) {
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

@Composable
fun AppVersion(
    versionText: String,
    copyrights: String,
    onClick: () -> Unit
) {
    Surface(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Box(
                modifier = Modifier.size(30.dp),
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    versionText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                )
                Text(
                    copyrights,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                )
            }
        }
    }
}