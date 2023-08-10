package com.danotech.rinfo.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.components.SettingSwitch
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    openAndPopUp: (String, String) -> Unit,
    onBackPressed: () -> Unit = {},
    onFabClicked: () -> Unit = {},
    onTabSelected: (RInfoScreen) -> Unit = {},
    onLogoutClicked: () -> Unit = {},
    onNavClicked: (String) -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
            }, fab = {
                FloatingActionButton(
                    onClick = onFabClicked,
                    modifier = Modifier.padding(bottom = 10.dp),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search)
                    )
                }
            })
        },
    ) { innerPadding ->
        EditAccountContent(
            openAndPopUp = openAndPopUp,
            innerPadding = innerPadding,
            settingType = SettingType.TEXT,
            onLogoutClicked = onLogoutClicked,
            onNavClicked = onNavClicked
        )
    }
}

@Composable
fun EditAccountContent(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
    settingType: SettingType,
    onLogoutClicked: () -> Unit,
    onNavClicked: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding,
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(
                    size = 100.dp,
                    imageUrI = R.drawable.cafe_javas
                )
                Spacer(modifier = Modifier.width(40.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    androidx.compose.material.Text(
                        text = "Cafe Javas",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    androidx.compose.material.Text(
                        text = "Kampala, Uganda",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Divider()
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            SettingsClickableComp(
                name = R.string.dark_mode,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.dark_mode,
                onClick = {

                }
            )
        }

        item {
            SettingsClickableComp(
                name = R.string.notifications,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.notifications,
            ) {
                // here you can do anything - navigate - open other settings, ...

            }
        }

        item {
            SettingsClickableComp(
                name = R.string.account,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.account,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.BusinessAccount.name)
                }
            )
        }

        item {
            SettingsClickableComp(
                name = R.string.profile,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.account,
                settingType = settingType,
                onClick = {
                    onNavClicked(RInfoScreen.EditAccount.name)
                }
            )
        }


        item {
            SettingsClickableComp(
                name = R.string.about,
                icon = Icons.Rounded.FavoriteBorder,
                iconDesc = R.string.about,
                settingType = settingType,
            ) {
                // here you can do anything - navigate - open other settings, ...
            }
        }


        item {
            SettingsClickableComp(
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsClickableComp(
    icon: ImageVector,
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    settingType: SettingType = SettingType.SWITCH,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        onClick = onClick,
    ) {
        Card(
            elevation = CardDefaults.cardElevation()
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
                    Text(
                        text = stringResource(id = name),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    if (settingType == SettingType.SWITCH) {
                        SettingSwitch(
                            switchOn = false,
                            onSwitchChanged = {},
                        )
                    } else {
                        Icon(
                            Icons.Rounded.KeyboardArrowRight,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = stringResource(id = R.string.arrow_forward)
                        )
                    }
                }
            }
        }
    }
}