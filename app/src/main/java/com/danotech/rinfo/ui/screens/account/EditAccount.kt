package com.danotech.rinfo.ui.screens.account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.TextInput
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.theme.AppTheme

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
            Spacer(modifier = Modifier.height(20.dp))
            ProfileImage(
                size = 150.dp,
                imageUrI = R.drawable.cafe_javas
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            TextInput(
                leadingIcon = Icons.Filled.AccountBox,
                value = "",
                labelText = "Account Name"
            )
        }

        item {
            TextInput(
                leadingIcon = Icons.Filled.AccountBox,
                value = "",
                labelText = "First Name"
            )
        }

        item {
            TextInput(
                leadingIcon = Icons.Filled.AccountBox,
                value = "",
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