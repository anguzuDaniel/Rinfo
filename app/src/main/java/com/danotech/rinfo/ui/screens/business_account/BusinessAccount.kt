package com.danotech.rinfo.ui.screens.business_account

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.TextInputWithLabel
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessAccount() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = "Business Account",
                isShowingHomePage = false,
                onBackButtonClicked = {
                    // Back button clicked
                },
            )
        },
    ) {
        BusinessAccountContent()
    }
}

@Composable
fun BusinessAccountContent(
    modifier: Modifier = Modifier
) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImage(
                size = 200.dp,
                imageUrI = R.drawable.cafe_javas
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Business Logo")
        }
        Spacer(modifier = Modifier.height(20.dp))

        TextInputWithLabel(
            labelText = "Business Name",
            value = "",
            onValueChanged = {})

        Spacer(modifier = Modifier.height(10.dp))

        TextInputWithLabel(
            labelText = "Description",
            value = "",
            onValueChanged = {})

        Spacer(modifier = Modifier.height(10.dp))

        RinfoButton(
            name = R.string.add_account,
            onClicked = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        )

        BusinessAccount()
    }
}

@Preview(showBackground = true)
@Composable
fun EditAccountPreview() {
    AppTheme {
        BusinessAccount()
    }
}

@Preview(showBackground = true)
@Composable
fun EditAccountDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        BusinessAccount()
    }
}