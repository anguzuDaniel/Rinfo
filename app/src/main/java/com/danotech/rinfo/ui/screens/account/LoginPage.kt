package com.danotech.rinfo.ui.screens.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.SubHeadingText
import com.danotech.rinfo.ui.components.TextInput
import com.example.compose.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // page title
        HeadingText(
            text = R.string.login,

            )

        // page sub title
        SubHeadingText(text = R.string.sign_in)

        TextInput(
            labelText = "Email",
            leadingIcon = Icons.Default.Person
        )

        TextInput(
            labelText = "Password",
            leadingIcon = Icons.Default.Lock
        )

        RinfoButton(
            name = R.string.login,
            onClicked = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginPage()
    }
}

@Preview
@Composable
fun LoginScreenDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        LoginPage()
    }
}