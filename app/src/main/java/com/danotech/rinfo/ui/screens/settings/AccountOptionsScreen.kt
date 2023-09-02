package com.danotech.rinfo.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RinfoBottomNavigation
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@Composable
fun AccountOptionsScreen(
    openAndPopUp: (String, String) -> Unit,
    onBackPressed: () -> Unit = {},
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
                title = stringResource(id = R.string.account),
                isShowingHomePage = false,
                onBackButtonClicked = onBackPressed,
            )
        },
    ) { innerPadding ->
        AccountOptionsContent(
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
fun AccountOptionsContent(
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

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.body_padding))
            .padding(paddingValues = innerPadding),
    ) {
        SettingsClickableComp(
            leadingIcon = Icons.Filled.AccountBox,
            name = R.string.profile,
            icon = Icons.Rounded.FavoriteBorder,
            iconDesc = R.string.account,
            settingType = settingType,
            onClick = {
                onNavClicked(RInfoScreen.EditAccount.name)
            }
        )

        SettingsClickableComp(
            leadingIcon = Icons.Filled.AccountBox,
            name = R.string.business_account,
            icon = Icons.Rounded.FavoriteBorder,
            iconDesc = R.string.about,
            settingType = settingType,
            onClick = {
                onNavClicked(RInfoScreen.BusinessAccount.name)
            }
        )
    }
}