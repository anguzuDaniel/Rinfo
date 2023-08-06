@file:OptIn(ExperimentalMaterial3Api::class)

package com.danotech.rinfo.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ClickableTextRow
import com.danotech.rinfo.ui.components.EmailField
import com.danotech.rinfo.ui.components.GoogleButton
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.PasswordField
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.SubHeadingText
import com.danotech.rinfo.ui.components.TextInput
import com.danotech.rinfo.ui.theme.AppTheme

@Composable
fun Login(
    modifier: Modifier = Modifier,
    onSignUpTextClicked: () -> Unit = { },
    onBackHandler: () -> Unit = { }
) {
    BackHandler() {
        onBackHandler()
    }
    Surface() {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.body_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // page title
            HeadingText(text = R.string.login)

            // page sub title
            SubHeadingText(text = R.string.sign_in)

            EmailField(
                value = "",
                onValueChanged = { /*TODO*/ },
            )

            PasswordField(
                value = "",
                onValueChanged = { /*TODO*/ },
            )

            RinfoButton(
                name = R.string.login,
                onClicked = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Divider()

            Spacer(modifier = Modifier.height(16.dp))

            GoogleButton(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            ClickableTextRow(
                clickableText = R.string.sign_up,
                noneClickableText = R.string.dont_have_an_account,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                onSignUpTextClicked = onSignUpTextClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    AppTheme {
        Login()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        Login()
    }
}