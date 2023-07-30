package com.danotech.rinfo.ui.screens.Account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.SubHeadingText
import com.danotech.rinfo.ui.components.TextInput
import com.example.compose.AppTheme

@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // page title
        HeadingText(
            text = R.string.create_account,
            modifier = modifier.padding(5.dp)
        )

        // sub title
        SubHeadingText(text = R.string.have_an_account)

        Spacer(modifier = modifier.height(20.dp))

        TextInput(
            labelText = "Name",
            leadingIcon = Icons.Default.Person,
        )

        TextInput(
            labelText = "Email",
            leadingIcon = Icons.Default.Email,
        )

        TextInput(
            labelText = "Password",
            leadingIcon = Icons.Default.Lock,
        )

        RinfoButton(
            name = R.string.create_account,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputPreview() {
    TextInput(
        labelText = "First Name",
        leadingIcon = Icons.Default.Person,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    AppTheme(
        darkTheme = false,
    ) {
        CreateAccountScreen()
    }
}