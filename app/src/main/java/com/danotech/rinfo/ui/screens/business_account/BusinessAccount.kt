package com.danotech.rinfo.ui.screens.business_account

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.common.ext.basicButton
import com.danotech.rinfo.ui.components.AddLogoImage
import com.danotech.rinfo.ui.components.BusinessAccountButton
import com.danotech.rinfo.ui.components.Loading
import com.danotech.rinfo.ui.components.TextInputWithLabel
import com.danotech.rinfo.ui.screens.account.SelectBusinessCategory
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.togitech.ccp.component.TogiCountryCodePicker
import com.togitech.ccp.data.utils.checkPhoneNumber
import com.togitech.ccp.data.utils.getDefaultLangCode
import com.togitech.ccp.data.utils.getDefaultPhoneCode
import com.togitech.ccp.data.utils.getLibCountries

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessAccount(
    modifier: Modifier = Modifier,
    viewModel: BusinessAccountViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
) {
    BackHandler {
        onBackClicked()
    }

    LaunchedEffect(viewModel) {
        viewModel.getBusinessAccount()
    }

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = if (!uiState.isLoading) {
            {
                RinfoTopAppBar(
                    title = "Business Account",
                    isShowingHomePage = false,
                    onBackButtonClicked = onBackClicked,
                )
            }
        } else {
            {}
        },
    ) { innerPadding ->
        if (!uiState.isLoading) {
            BusinessAccountContent(
                innerPadding = innerPadding,
                viewModel = viewModel
            )
        } else {
            Loading()
        }
    }
}

@Composable
fun BusinessAccountContent(
    viewModel: BusinessAccountViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    val uiState = viewModel.uiState.collectAsState().value

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding
    ) {
        item {
            TextInputWithLabel(
                labelText = stringResource(R.string.business_name),
                placeholder = R.string.cake_business,
                value = uiState.name,
                onValueChanged = viewModel::onNameChange
            )
        }

        item {
            TextInputWithLabel(
                labelText = stringResource(R.string.description),
                placeholder = R.string.placeholder_business_description,
                value = uiState.description,
                onValueChanged = viewModel::onDescriptionChange
            )
        }

        item {
            TextInputWithLabel(
                labelText = stringResource(R.string.address),
                value = uiState.address,
                placeholder = R.string.placeholder_business_address,
                onValueChanged = viewModel::onAddressChange
            )
        }

        item {
            TextInputWithLabel(
                labelText = stringResource(id = R.string.phone),
                value = uiState.phone,
                placeholder = R.string.placeholder_business_phone,
                onValueChanged = viewModel::onPhoneChange
            )
        }

        item {
            TextInputWithLabel(
                labelText = stringResource(id = R.string.whatsapp),
                value = uiState.whatsapp,
                placeholder = R.string.whatsapp,
                onValueChanged = viewModel::onWhatsappChange
            )
        }

        item {
            TextInputWithLabel(
                labelText = stringResource(id = R.string.email),
                value = uiState.email,
                placeholder = R.string.placeholder_business_email,
                onValueChanged = viewModel::onEmailChange
            )
        }

        item {
            SelectBusinessCategory(
                modifier = Modifier,
                onAccountTypeSelected = {
                    viewModel.onCategoryChange(it)
                },
            )
        }

        item {
            AddLogoImage(
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicButton()
            ) {}
        }

        item {
            BusinessAccountButton(
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicButton()
            ) {
                viewModel.onBusinessAccountCreated()
            }
        }
    }
}

@Composable
fun InputWithCountryCode(
    text: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    val getDefaultLangCode = getDefaultLangCode() // Auto detect language
    val getDefaultPhoneCode = getDefaultPhoneCode() // Auto detect phone code : +90
    var phoneCode by rememberSaveable { mutableStateOf(getDefaultPhoneCode) }
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode) }
    val verifyText by remember { mutableStateOf("") }
    val isValidPhone by remember { mutableStateOf(true) }

    Surface {
        Column {
            Text(
                text = verifyText,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            )
            TogiCountryCodePicker(
                pickedCountry = {
                    phoneCode = it.countryPhoneCode
                    defaultLang = it.countryCode
                },
                defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                dialogAppBarTextColor = MaterialTheme.colorScheme.primary,
                dialogAppBarColor = MaterialTheme.colorScheme.primary,
                error = isValidPhone,
                text = text,
                onValueChange = onTextChange,
            )

            val fullPhoneNumber = "$phoneCode${phoneNumber.value}"
            val checkPhoneNumber = checkPhoneNumber(
                phone = phoneNumber.value,
                fullPhoneNumber = fullPhoneNumber,
                countryCode = defaultLang
            )
        }
        Spacer(modifier = modifier.height(5.dp))
    }
}