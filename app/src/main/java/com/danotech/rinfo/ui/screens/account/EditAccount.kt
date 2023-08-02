package com.danotech.rinfo.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.TextInput
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.example.compose.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAccount() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = "edit Account",
                isShowingHomePage = false,
                onBackButtonClicked = {
                    // Back button clicked
                },
            )
        },
    ) { innerPadding ->
        EditAccountContent(
            innerPadding = innerPadding
        )
    }
}

@Composable
fun EditAccountContent(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(id = R.string.profile_image)
            )
        }

        item {
            TextInput(
                leadingIcon = Icons.Filled.AccountBox,
                labelText = "Account Name"
            )
        }

        item {
            TextInput(
                leadingIcon = Icons.Filled.AccountBox,
                labelText = "First Name"
            )
        }

        item {
            TextInput(
                leadingIcon = Icons.Filled.AccountBox,
                labelText = "Last Name"
            )
        }

        item {
            RinfoButton(
                name = R.string.save,
                onClicked = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditAccountPreview() {
    AppTheme {
        EditAccount()
    }
}

@Preview(showBackground = true)
@Composable
fun EditAccountDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        EditAccount()
    }
}