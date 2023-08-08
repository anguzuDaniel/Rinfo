package com.danotech.rinfo.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.SaveProfileButton
import com.danotech.rinfo.ui.components.TextInput
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = "Profile",
                isShowingHomePage = false,
                onBackButtonClicked = {
                    // Back button clicked
                },
            )
        },
    ) { innerPadding ->
        ProfileContent(
            innerPadding = innerPadding,
            viewModel = viewModel,
        )
    }
}

@Composable
fun ProfileContent(
    viewModel: ProfileViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.value

    val profile = viewModel.getProfile()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding))
            .fillMaxHeight()
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        ProfileImage(
            size = 150.dp,
            imageUrI = R.drawable.cafe_javas
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextInput(
            leadingIcon = Icons.Filled.AccountBox,
            value = uiState.profileName,
            onValueChanged = viewModel::onProfileNameChanged,
            labelText = "Account Name"
        )

        TextInput(
            leadingIcon = Icons.Filled.AccountBox,
            value = uiState.profileFirstName,
            onValueChanged = viewModel::onProfileFirstNameChanged,
            labelText = "First Name"
        )

        TextInput(
            leadingIcon = Icons.Filled.AccountBox,
            value = uiState.profileLastName,
            onValueChanged = viewModel::profileLastNameChanged,
            labelText = "Last Name"
        )

        SaveProfileButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        ) {
            viewModel.saveProfile()
        }
    }
}