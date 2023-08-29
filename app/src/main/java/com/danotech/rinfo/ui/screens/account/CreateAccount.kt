package com.danotech.rinfo.ui.screens.account

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.local.Category
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
    val createAccountUiState by viewModel.uiState

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
            }

//            item {
//                /**
//                 * First name text input
//                 */
//                TextInput(
//                    value = createAccountUiState.name,
//                    onValueChanged = viewModel::onNameChanged,
//                    labelText = stringResource(R.string.name),
//                    leadingIcon = Icons.Default.Person,
//                )
//            }

            item {
                EmailField(
                    value = createAccountUiState.email,
                    onValueChanged = viewModel::onEmailChanged,
                )
            }

            item {
                PasswordField(
                    value = createAccountUiState.password,
                    onValueChanged = viewModel::onPasswordChanged,
                )
            }

            item {
                RepeatPasswordField(
                    value = createAccountUiState.confirmPassword,
                    onValueChanged = viewModel::onConfirmPasswordChanged,
                )
            }

//            item {
//                SelectAccountType(
//                    modifier = Modifier,
//                    onAccountTypeSelected = viewModel::onAccountTypeSelected
//                )
//            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                SignUpButton(modifier = Modifier.fillMaxWidth(), onClick = viewModel::onSignUpClick)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBusinessCategory(
    modifier: Modifier = Modifier,
    onAccountTypeSelected: (Category) -> Unit = {}
) {
    val listItems = LocalReviewProvider.categories

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
                value = selectedItem.name,
                onValueChange = { onAccountTypeSelected(selectedItem) },
                readOnly = true,
                label = { Text(text = stringResource(id = R.string.business_type)) },
                trailingIcon = {
                    TrailingIcon(
                        expanded = expanded,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Category,
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
                        text = { Text(text = selectedOption.name) },
                        onClick = {
                            selectedItem = selectedOption
                            onAccountTypeSelected(selectedOption)
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Category,
                                contentDescription = selectedOption.name
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}