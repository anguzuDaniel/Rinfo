package com.danotech.rinfo.ui.screens.profile

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ProfileButton
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.TextInputWithLabel
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    BackHandler {
        onBackClicked()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = "Profile",
                isShowingHomePage = false,
                onBackButtonClicked = onBackClicked,
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

//    val profile = viewModel.getProfile()

    LazyColumn(
        modifier = modifier
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
                    Text(
                        text = "Cafe Javas",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kampala, Uganda",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Divider()
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileName,
                onValueChanged = viewModel::onProfileNameChanged,
                labelText = stringResource(R.string.profile_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileFirstName,
                onValueChanged = viewModel::onProfileFirstNameChanged,
                labelText = stringResource(R.string.first_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileLastName,
                onValueChanged = viewModel::profileLastNameChanged,
                labelText = stringResource(R.string.last_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            ProfileButton(
                modifier = Modifier
                    .fillMaxWidth(),
                isLoading = uiState.isLoading,
            ) {
                viewModel.saveProfile()
            }
        }
    }
}