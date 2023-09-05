package com.danotech.rinfo.ui.screens.business_account

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.data.LocalOfflineDatabase
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.AccountService
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@Suppress("REPLACED_WITH_EXPRESSION")
@HiltViewModel
class BusinessAccountViewModel @Inject constructor(
    private val accountService: AccountService,
    private val businessAccountService: BusinessAccountService,
    private val localOfflineDatabase: LocalOfflineDatabase,
    logService: LogService
) : RinfoViewModel(logService) {
    private val _uiState = MutableStateFlow(BusinessAccountUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        launchCatching {
            val business = businessAccountService.currentUserBusinessAccount

            business.map { accounts ->
                if (accounts.isNotEmpty()) {
                    val account = accounts[0]
                    _uiState.value = BusinessAccountUiState(
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
        _uiState.value = _uiState.value.copy(name = newValue)

    }

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)

    }

    fun onPhoneChange(newValue: String) {
        _uiState.value = _uiState.value.copy(phone = newValue)

    }

    fun onWhatsappChange(newValue: String) {
        _uiState.value = _uiState.value.copy(whatsapp = newValue)

    }

    fun onAddressChange(newValue: String) {
        _uiState.value = _uiState.value.copy(address = newValue)

    }

    fun onDescriptionChange(newValue: String) {
        _uiState.value = _uiState.value.copy(description = newValue)
    }


    fun onCategoryChange(newValue: Category) {
        _uiState.value = _uiState.value.copy(businessCategory = newValue)
    }

    fun openBottomSheet(open: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomSheet = open)
    }

    fun setDefaultImage(image: Bitmap) {
        _uiState.value = _uiState.value.copy(profilePicture = image)
    }


    fun onBusinessAccountCreated() {
        if (_uiState.value.name.isEmpty()) {
            SnackbarManager.showMessage(R.string.name_is_required)
            return
        }

        if (_uiState.value.email.isEmpty()) {
            SnackbarManager.showMessage(R.string.email_is_required)
            return
        }

        if (_uiState.value.phone.isEmpty()) {
            SnackbarManager.showMessage(R.string.phone_is_required)
            return
        }

        if (_uiState.value.address.isEmpty()) {
            SnackbarManager.showMessage(R.string.address_is_required)
            return
        }

        if (_uiState.value.description.isEmpty()) {
            SnackbarManager.showMessage(R.string.description_is_required)
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true) // Set loading to true

        launchCatching {
            val updateUiState = uiState.value

            val business = Business(
                userId = FirebaseAuth.getInstance().currentUser!!.email.toString(),
                name = updateUiState.name,
                email = updateUiState.email,
                phone = updateUiState.phone,
                whatsapp = updateUiState.whatsapp,
                address = updateUiState.address,
                description = updateUiState.description,
                businessCategory = updateUiState.businessCategory.name,
                logo = updateUiState.logo,
                reviews = 0,
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
            _uiState.value = _uiState.value.copy(isLoading = false) // Set loading to false
        }
    }

    fun getBusinessAccount(userId: String) {
        launchCatching {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                imageLoading = true
            )

            val business =
                businessAccountService.getBusinessById(FirebaseAuth.getInstance().currentUser!!.email.toString())

            if (business != null) {
                // Inside your function
                val storage = FirebaseStorage.getInstance()
                val storageRef = storage.reference

                val imageName = "${userId}.jpg"
                // Replace with the actual image name
                val imageRef = storageRef.child("logos/${imageName}")

                imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                    // Successfully retrieved image bytes
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    // Use the bitmap as needed (e.g., display in ImageView)
                    _uiState.value = _uiState.value.copy(
                        profilePicture = bitmap,
                        imageLoading = false
                    )
                }.addOnFailureListener {
                    // Handle failure
                }

                _uiState.value = _uiState.value.copy(
                    name = business.name,
                    email = business.email,
                    phone = business.phone,
                    address = business.address,
                    description = business.description,
                    whatsapp = business.whatsapp,
                    businessCategory = getCategory(business.businessCategory),
                    logo = business.logo
                )
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    /**
     * Gets image from firebase storage
     * @param userId
     * uses the user id to get the image
     */
    fun getImageFromFireBase(userId: String) {
        val storageRef = Firebase.storage.reference
        val imageRef = storageRef.child("logos/${userId}.jpg")

        val ONE_MEGABYTE: Long = 1024 * 1024
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
            val bmp: Bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            _uiState.value = _uiState.value.copy(profilePicture = bmp)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    /**
     * Uploads image to firebase storage
     * @param image
     * uses the user id as the name of the image
     */
    fun upLoadImageToFireBase(image: Bitmap, userId: String) {
        // Inside your Composable function
        val storageRef = Firebase.storage.reference

        launchCatching {
            // sets loading to true
            _uiState.value = _uiState.value.copy(isLoading = true)

            val imageName = "${userId}.jpg"
            val imageRef = storageRef.child("logos/${imageName}")

            val stream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            val data = stream.toByteArray()
            val uploadTask = imageRef.putBytes(data)

            uploadTask.addOnSuccessListener {
                SnackbarManager.showMessage(R.string.image_uploaded_successfully)
            }.addOnFailureListener {
                // Handle upload failure
                SnackbarManager.showMessage(R.string.something_went_wrong)
            }

            _uiState.value = _uiState.value.copy(logo = data.toString())
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun getAllCategories() = localOfflineDatabase.categoryDao().getAllCategories()

    private fun getCategory(name: String): Category {
        return Category(name = name)
    }
}