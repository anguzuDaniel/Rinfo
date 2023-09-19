package com.danotech.rinfo.ui.screens.settings

import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Password
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.centeredTopAppBar
import com.danotech.rinfo.ui.screens.RInfoScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AccountOptionsScreen(
    openAndPopUp: (String, String) -> Unit,
    onBackClick: () -> Unit = {},
    onNavClicked: (String) -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel(),
    themeViewModel: ThemeViewModel = hiltViewModel(),
    window: Window
) {
    BackHandler {
        onBackClick()
    }

    LaunchedEffect(viewModel) {
        viewModel.getProfile()
    }

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
                onBackClick = onBackClick,
                text = R.string.About,
                hasBack = true
            )
        },
    ) { innerPadding ->
        AccountOptionsContent(
            openAndPopUp = openAndPopUp,
            innerPadding = innerPadding,
            settingType = SettingType.TEXT,
            onNavClicked = onNavClicked,
            viewModel = viewModel
        )
    }
}

@Composable
fun AccountOptionsContent(
    openAndPopUp: (String, String) -> Unit,
    innerPadding: PaddingValues,
    settingType: SettingType,
    onNavClicked: (String) -> Unit = {},
    viewModel: SettingsViewModel,
) {
    viewModel.uiState.collectAsState().value

    var isShowingDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.body_padding))
            .padding(paddingValues = innerPadding),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SettingsClickableComp(
            hasImage = true,
            leadingIcon = Icons.Filled.AccountBox,
            description = "Edit your profile",
            iconDesc = R.string.account,
            name = R.string.profile,
            settingType = settingType,
            onClick = {
                onNavClicked(RInfoScreen.EditAccount.name)
            }
        )

        SettingsClickableComp(
            hasImage = true,
            leadingIcon = Icons.Filled.AccountBox,
            description = "Edit your Business Account",
            iconDesc = R.string.about,
            name = R.string.business_account,
            settingType = settingType,
            onClick = {
                onNavClicked(RInfoScreen.BusinessAccount.name)
            }
        )

        SettingsClickableComp(
            leadingIcon = Icons.Filled.Password,
            description = "Change your password",
            iconDesc = R.string.change_password,
            name = R.string.change_password,
            settingType = settingType,
            onClick = {
                onNavClicked(RInfoScreen.ChangePassword.name)
            }
        )

        SettingsClickableComp(
            leadingIcon = Icons.Filled.DeleteForever,
            iconDesc = R.string.delete_account,
            name = R.string.delete_account,
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
                            viewModel.deleteAccount(openAndPopUp)
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