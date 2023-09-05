package com.danotech.rinfo.ui.screens.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.screens.RInfoScreen
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
    var isShowingDialog by remember {
        mutableStateOf(false)
    }

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
            },
            description = "Edit your profile"
        )

        SettingsClickableComp(
            leadingIcon = Icons.Filled.AccountBox,
            name = R.string.business_account,
            icon = Icons.Rounded.FavoriteBorder,
            iconDesc = R.string.about,
            settingType = settingType,
            onClick = {
                onNavClicked(RInfoScreen.BusinessAccount.name)
            },
            description = "Edit your Business Account"
        )

        SettingsClickableComp(
            leadingIcon = Icons.Filled.Password,
            name = R.string.change_password,
            icon = Icons.Rounded.FavoriteBorder,
            iconDesc = R.string.change_password,
            settingType = settingType,
            onClick = {
                onNavClicked(RInfoScreen.ChangePassword.name)
            },
            description = "Change your password"
        )

        SettingsClickableComp(
            leadingIcon = Icons.Filled.DeleteForever,
            name = R.string.delete_account,
            icon = Icons.Rounded.FavoriteBorder,
            iconDesc = R.string.delete_account,
            settingType = settingType,
            onClick = {
                isShowingDialog = true
            },
            opensDialogWhenClicked = true
        )

        if (isShowingDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    isShowingDialog = false
                },
                title = {
                    Text(text = stringResource(R.string.delete_account))
                },
                text = {
                    Text(text = stringResource(R.string.delete_account_warning))
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            isShowingDialog = false
                        }
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            isShowingDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}