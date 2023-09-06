package com.danotech.rinfo.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.ClickableTextRow
import com.danotech.rinfo.ui.components.EmailField
import com.danotech.rinfo.ui.components.GoogleButton
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.OrFormDiver
import com.danotech.rinfo.ui.components.PasswordField
import com.danotech.rinfo.ui.components.RepeatPasswordField
import com.danotech.rinfo.ui.components.SignUpButton

/**
 * Create Account page
 */
@Composable
fun CreateAccount(
    modifier: Modifier = Modifier,
    viewModel: CreateAccountViewModel = hiltViewModel(),
    onSignInTextClicked: () -> Unit = { },
    onBackHandler: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState().value

    BackHandler {
        onBackHandler()
    }

    Scaffold {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.body_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true,
            contentPadding = it
        ) {
            item {
                // page title
                HeadingText(
                    text = R.string.create_account, modifier = Modifier.padding(5.dp)
                )
            }

            item {
                ClickableTextRow(
                    clickableText = R.string.sign_in,
                    noneClickableText = R.string.have_an_account,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    onSignUpTextClicked = onSignInTextClicked
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                AnimatedVisibility(visible = uiState.isCreateAccountError) {
                    Text(
                        text = uiState.errorMessage,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                EmailField(
                    value = uiState.email,
                    onValueChanged = viewModel::onEmailChanged,
                )
            }

            item {
                PasswordField(
                    value = uiState.password,
                    onValueChanged = viewModel::onPasswordChanged,
                )
            }

            item {
                RepeatPasswordField(
                    value = uiState.confirmPassword,
                    onValueChanged = viewModel::onConfirmPasswordChanged,
                )
            }


            item {
                Spacer(modifier = Modifier.height(10.dp))
                SignUpButton(
                    isLoading = uiState.isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = viewModel::onSignUpClick
                ) {

                }
            }

            item {
                OrFormDiver()
            }

            item {
                GoogleButton(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}