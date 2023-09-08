package com.danotech.rinfo.ui.screens.login

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
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
    onBackHandler: () -> Unit = { },
    onResetPassword: () -> Unit = { },
) {
    val spaceLarge = 16.dp
    val uiState = viewModel.uiState.collectAsState().value
    var isRememberMeChecked by remember {
        mutableStateOf(false)
    }

    BackHandler {
        onBackHandler()
    }

    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.body_padding))
                .padding(paddingValues = it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(spaceLarge))
            // page title
            HeadingText(text = R.string.login)

            // page sub title
            SubHeadingText(text = R.string.sign_in)
            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(visible = uiState.hasMessage) {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.44f),
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            EmailField(
                value = uiState.email,
                onValueChanged = viewModel::onEmailChange,
            )

            PasswordField(
                value = uiState.password,
                onValueChanged = viewModel::onPasswordChange,
            )

            SignInButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicButton()
            ) {
                viewModel.signInClick(openAndPopUp)
            }

            Spacer(modifier = Modifier.height(spaceLarge))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isRememberMeChecked,
                        onCheckedChange = {
                            isRememberMeChecked = true
                        },
                        modifier = Modifier.size(10.dp)
                    )

                    Text(text = stringResource(id = R.string.remember_me))
                }

                ClickableText(
                    text = AnnotatedString(text = stringResource(id = R.string.reset_password)),
                    onClick = {
                        onResetPassword()
                    },
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                    ),
                )
            }

            Spacer(modifier = Modifier.height(spaceLarge))

            OrFormDiver()

            Spacer(modifier = Modifier.height(spaceLarge))

            GoogleButton(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(spaceLarge))

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