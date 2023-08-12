package com.danotech.rinfo.ui.screens.business_account

import androidx.compose.runtime.mutableStateOf
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Suppress("REPLACED_WITH_EXPRESSION")
@HiltViewModel
class BusinessAccountViewModel @Inject constructor(
    private val accountService: AccountService,
    private val businessAccountService: BusinessAccountService,
    logService: LogService
) : RinfoViewModel(logService) {
    val uiState = mutableStateOf(BusinessAccountUiState())


    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        launchCatching {
            val business = businessAccountService.currentUserBusinessAccount

            business.map { accounts ->
                if (accounts.isNotEmpty()) {
                    val account = accounts[0]
                    uiState.value = BusinessAccountUiState(
                        name = account.name,
                        email = account.email,
                        phone = account.phone,
                        address = account.address,
                        description = account.description,
                        businessCategory = getCategory(account.businessCategory)
                    )
                }
            }
        }
    }

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)

    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)

    }

    fun onPhoneChange(newValue: String) {
        uiState.value = uiState.value.copy(phone = newValue)

    }

    fun onAddressChange(newValue: String) {
        uiState.value = uiState.value.copy(address = newValue)

    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }


    fun onCategoryChange(newValue: Category) {
        uiState.value = uiState.value.copy(businessCategory = newValue)
    }


    fun onBusinessAccountCreated() {
        if (uiState.value.name.isEmpty()) {
            SnackbarManager.showMessage(R.string.name_is_required)
            return
        }

        if (uiState.value.email.isEmpty()) {
            SnackbarManager.showMessage(R.string.email_is_required)
            return
        }

        if (uiState.value.phone.isEmpty()) {
            SnackbarManager.showMessage(R.string.phone_is_required)
            return
        }

        if (uiState.value.address.isEmpty()) {
            SnackbarManager.showMessage(R.string.address_is_required)
            return
        }

        if (uiState.value.description.isEmpty()) {
            SnackbarManager.showMessage(R.string.description_is_required)
            return
        }

        uiState.value = uiState.value.copy(isLoading = true) // Set loading to true

        launchCatching {
            val updateUiState = uiState.value

            val business = Business(
                userId = FirebaseAuth.getInstance().currentUser!!.email.toString(),
                name = updateUiState.name,
                email = updateUiState.email,
                phone = updateUiState.phone,
                address = updateUiState.address,
                description = updateUiState.description,
                businessCategory = updateUiState.businessCategory.name,
                reviews = 0
            )
            if (businessAccountService.getBusinessById(accountService.currentUserId) == null) {
                businessAccountService.create(business)
                SnackbarManager.showMessage(R.string.business_account_created)
            } else {
                businessAccountService.update(business)
                SnackbarManager.showMessage(R.string.business_account_updated)
            }
        }.invokeOnCompletion {
            // This block will be called when launchCatching completes
            uiState.value = uiState.value.copy(isLoading = false) // Set loading to false
        }
    }

    private fun getCategory(name: String): Category {
        return Category(name = name)
    }
}