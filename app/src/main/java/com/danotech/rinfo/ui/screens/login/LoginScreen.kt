@file:OptIn(ExperimentalMaterial3Api::class)

package com.danotech.rinfo.ui.screens.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.common.ext.basicButton
import com.danotech.rinfo.ui.components.ClickableTextRow
import com.danotech.rinfo.ui.components.EmailField
import com.danotech.rinfo.ui.components.GoogleButton
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.OrFormDiver
import com.danotech.rinfo.ui.components.PasswordField
import com.danotech.rinfo.ui.components.SignInButton
import com.danotech.rinfo.ui.components.SubHeadingText

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpTextClicked: () -> Unit = { },
    onBackHandler: () -> Unit = { }
) {
    val loginUiState = viewModel.uiState.value

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
                value = loginUiState.email,
                onValueChanged = viewModel::onEmailChange,
            )

            PasswordField(
                value = loginUiState.password,
                onValueChanged = viewModel::onPasswordChange,
            )

            SignInButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicButton()
            ) {
                viewModel.signInClick(openAndPopUp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OrFormDiver()

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