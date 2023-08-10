package com.danotech.rinfo.ui.screens.business_account

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.common.ext.basicButton
import com.danotech.rinfo.ui.components.BasicButton
import com.danotech.rinfo.ui.components.BusinessAccountButton
import com.danotech.rinfo.ui.components.TextInputWithLabel
import com.danotech.rinfo.ui.screens.account.SelectBusinessCategory
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessAccount(
    viewModel: BusinessAccountViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackClicked()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = "Business Account",
                isShowingHomePage = false,
                onBackButtonClicked = onBackClicked,
            )
        },
    ) { innerPadding ->
        BusinessAccountContent(
            innerPadding = innerPadding,
            viewModel = viewModel
        )
    }
}

@Composable
fun BusinessAccountContent(
    viewModel: BusinessAccountViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val uiState = viewModel.uiState.value

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding
    ) {
        item {

            TextInputWithLabel(
                labelText = "Business Name",
                placeholder = R.string.cake_business,
                value = uiState.name,
                onValueChanged = viewModel::onNameChange
            )
        }

        item {
            TextInputWithLabel(
                labelText = "Description",
                placeholder = R.string.placeholder_business_description,
                value = uiState.description,
                onValueChanged = viewModel::onDescriptionChange
            )
        }


        item {
            TextInputWithLabel(
                labelText = "Address",
                value = uiState.address,
                placeholder = R.string.placeholder_business_address,
                onValueChanged = viewModel::onAddressChange
            )
        }



        item {
            TextInputWithLabel(
                labelText = "phone",
                value = uiState.phone,
                placeholder = R.string.placeholder_business_phone,
                onValueChanged = viewModel::onPhoneChange
            )
        }


        item {
            TextInputWithLabel(
                labelText = "email",
                value = uiState.email,
                placeholder = R.string.placeholder_business_email,
                onValueChanged = viewModel::onEmailChange
            )
        }

        item {
            SelectBusinessCategory(
                modifier = Modifier,
                onAccountTypeSelected = {
                    viewModel.onCategoryChange(it)
                },
            )
        }

        item {
            BasicButton(
                text = R.string.add_logo,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .basicButton()
            ) {

            }
        }

        item {

            BusinessAccountButton(
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicButton()
            ) {
                viewModel.onBusinessAccountCreated()
            }
        }
    }
}