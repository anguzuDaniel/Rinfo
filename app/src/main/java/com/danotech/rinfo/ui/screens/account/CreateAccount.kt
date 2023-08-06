package com.danotech.rinfo.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.ui.components.ClickableTextRow
import com.danotech.rinfo.ui.components.GoogleButton
import com.danotech.rinfo.ui.components.HeadingText
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.TextInput
import com.danotech.rinfo.ui.theme.AppTheme

@Composable
fun CreateAccount(
    createAccountUiState: CreateAccountUiState,
    modifier: Modifier = Modifier,
    viewModel: CreateAccountViewModel = hiltViewModel(),
    onSignInTextClicked: () -> Unit = { },
    onBackHandler: () -> Unit = {}
) {
    BackHandler {
        onBackHandler()
    }

    Surface {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.body_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            userScrollEnabled = true
        ) {
            item {
                // page title
                HeadingText(
                    text = R.string.create_account,
                    modifier = Modifier.padding(5.dp)
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
            }

            item {
                /**
                 * First name text input
                 */
                TextInput(
                    value = TextFieldValue(createAccountUiState.name),
                    onValueChanged = {
                        viewModel.onFirstNameChanged(createAccountUiState.name)
                    },
                    labelText = stringResource(R.string.name),
                    leadingIcon = Icons.Default.Person,
                )
            }

            item {
                TextInput(
                    value = TextFieldValue(createAccountUiState.email),
                    onValueChanged = {
                        viewModel.onEmailChanged(createAccountUiState.email)
                    },
                    labelText = stringResource(R.string.email),
                    leadingIcon = Icons.Default.Email,
                )
            }

            item {
                TextInput(
                    value = TextFieldValue(createAccountUiState.password),
                    onValueChanged = {
                        viewModel.onPasswordChanged(createAccountUiState.password)
                    },
                    labelText = stringResource(R.string.password),
                    leadingIcon = Icons.Filled.Lock,
                )
            }

            item {
                TextInput(
                    value = TextFieldValue(createAccountUiState.confirmPassword),
                    onValueChanged = {
                        viewModel.onConfirmPasswordChanged(createAccountUiState.confirmPassword)
                    },
                    labelText = stringResource(R.string.confirm_password),
                    leadingIcon = Icons.Default.Lock,
                )
            }

            item {
                SelectAccountType(
                    modifier = Modifier
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                RinfoButton(
                    name = R.string.create_account,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 10.dp)
                    )
                    Text(
                        text = "OR", modifier = Modifier
                            .padding(10.dp)
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 10.dp)
                    )
                }
            }

            item {
                GoogleButton(
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAccountType(
    modifier: Modifier = Modifier,
) {
    val listItems = LocalReviewProvider.accountOptions

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    val focusRequester = remember {
        FocusRequester()
    }


    Box(
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        ) {

            TextField(
                value = stringResource(id = selectedItem.name),
                onValueChange = {},
                readOnly = true,
                label = { Text(text = stringResource(id = R.string.account_type)) },
                trailingIcon = {
                    TrailingIcon(
                        expanded = expanded,
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = selectedItem.icon),
                        contentDescription = stringResource(R.string.account_type_icon)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            // menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            ) {
                // this is a column scope
                // all the items are added vertically
                listItems.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = selectedOption.name)) },
                        onClick = {
                            selectedItem = selectedOption
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = selectedOption.icon),
                                contentDescription = stringResource(id = selectedOption.name)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountPreview() {
    AppTheme {
        CreateAccount(
            createAccountUiState = CreateAccountUiState()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountDarkPreview() {
    AppTheme(
        darkTheme = true,
    ) {
        CreateAccount(
            createAccountUiState = CreateAccountUiState()
        )
    }
}