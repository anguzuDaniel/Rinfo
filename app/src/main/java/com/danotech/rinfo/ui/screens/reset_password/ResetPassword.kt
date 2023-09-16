package com.danotech.rinfo.ui.screens.reset_password

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.common.ext.isValidEmail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPassword(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val uiState = viewModel.uiState.collectAsState().value

    val showChangePage = remember {
        mutableStateOf(false)
    }

    BackHandler {
        onBackClick()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.Cancel, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            if (showChangePage.value) {
                ChangePassword(
                    innerPadding = innerPadding,
                    uiState = uiState,
                    viewModel = viewModel
                )
            } else {
                RequestRestPassword(
                    innerPadding = innerPadding,
                    uiState = uiState,
                    viewModel = viewModel
                )
            }
        }
    )
}

// changes password
@Composable
fun ChangePassword(
    uiState: ResetPasswordUiState,
    viewModel: ResetPasswordViewModel,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    var isPasswordChanged by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(visible = uiState.hasMessage) {
            Text(
                text = uiState.message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.44f),
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        OutlinedTextField(
            value = uiState.newPassword,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("New Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = {
                Text(
                    text = "Enter your email",
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "email")
            },
        )

        Button(
            onClick = {
                // Validate and change password logic here
                if (uiState.newPassword.isNotEmpty()) {
                    // Password change successful
                    isPasswordChanged = true
                } else {
                    // Display an error or toast message for invalid input
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Change Password",
                color = Color.White
            )
        }

        if (isPasswordChanged) {
            Text(
                "Password changed successfully!",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun RequestRestPassword(
    uiState: ResetPasswordUiState,
    viewModel: ResetPasswordViewModel,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(innerPadding)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = uiState.hasError || uiState.hasMessage) {
            Text(
                text = uiState.message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.44f),
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChanged,
            label = { Text("Please your email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = {
                Text(text = "Enter your email")
            }
        )

        Button(
            onClick = viewModel::requestRestPasswordLink,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            enabled = uiState.email.isValidEmail(),
            shape = MaterialTheme.shapes.small
        ) {
            Row(
                horizontalArrangement = if (uiState.isLoading) Arrangement.spacedBy(5.dp) else Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Request reset password link",
                    color = Color.White
                )

                AnimatedVisibility(visible = uiState.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}