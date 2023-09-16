package com.danotech.rinfo.ui.screens.business_account

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.R
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.data.LocalOfflineDatabase
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.model.local.Category
import com.danotech.rinfo.model.service.BusinessAccountService
import com.danotech.rinfo.model.service.LogService
import com.danotech.rinfo.ui.screens.RinfoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@Suppress("REPLACED_WITH_EXPRESSION")
@HiltViewModel
class BusinessAccountViewModel @Inject constructor(
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
                        logo = account.logo,
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


    fun onCategoryChange(cat: Category) {
        _uiState.value = _uiState.value.copy(businessCategory = cat)
    }

    fun openBottomSheet(open: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomSheet = open)
    }

    fun onBusinessAccountCreated(
        onSuccess: () -> Unit = {}, onFailure: (String) -> Unit = {}
    ) {
        if (_uiState.value.name.isEmpty()) {
            SnackbarManager.showMessage(R.string.name_is_required)
            _uiState.value =
                _uiState.value.copy(hasMessage = true, message = "Business Name is Required")
            return
        }

        if (_uiState.value.email.isEmpty()) {
            SnackbarManager.showMessage(R.string.email_is_required)
            _uiState.value =
                _uiState.value.copy(hasMessage = true, message = "Business email is required")
            return
        }

        if (_uiState.value.phone.isEmpty()) {
            SnackbarManager.showMessage(R.string.phone_is_required)
            _uiState.value =
                _uiState.value.copy(hasMessage = true, message = "Business email is required")
            return
        }

        if (_uiState.value.address.isEmpty()) {
            SnackbarManager.showMessage(R.string.address_is_required)
            _uiState.value =
                _uiState.value.copy(hasMessage = true, message = "Business address is required")
            return
        }

        if (_uiState.value.description.isEmpty()) {
            SnackbarManager.showMessage(R.string.description_is_required)
            _uiState.value =
                _uiState.value.copy(hasMessage = true, message = "Business Description")
            return
        }

        if (_uiState.value.businessCategory.name.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                hasMessage = true, message = "Please Provide a business Category"
            )
            return
        }


        launchCatching {
            _uiState.value = _uiState.value.copy(isLoading = true) // Set loading to true

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
                logo = updateUiState.logo, // Use the download URL here
                reviews = 0,
            )

            Log.d("Business: ", business.toString())

            // Update information in the database
            businessAccountService.update(business)

            // Show a success message or handle errors as needed
            _uiState.value = _uiState.value.copy(
                hasMessage = true,
                message = "You business account has been updated!",
                dialogOpened = true
            )
        }.invokeOnCompletion { throwable ->
            if (throwable != null) {
                // Handle the error here, log it, and provide feedback to the user
//                SnackbarManager.showMessage(message = "An error occurred: ${throwable.message}")
                onFailure("An error occurred: ${throwable.message}")                // runs when operation is successful
                _uiState.value = _uiState.value.copy(
                    hasMessage = true,
                    message = "An error occurred: ${throwable.message}",
                    isLoading = false,
                    dialogOpened = true
                )
            } else {
                // Operation was successful
                onSuccess()

                // runs when operation is successful
                _uiState.value = _uiState.value.copy(
                    hasMessage = true,
                    message = "Congratulation!! you have successfully created a business account.",
                    isLoading = false,
                    dialogOpened = true
                )
            }
        }
    }

    fun getBusinessAccount(userId: String) {
        launchCatching {
            _uiState.value = _uiState.value.copy(
                isLoading = true, imageLoading = true
            )

            val business =
                businessAccountService.getBusinessById(FirebaseAuth.getInstance().currentUser!!.email.toString())

            if (business != null) {
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

                Log.d(TAG, "getBusinessAccount: $business")
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
            _uiState.value = _uiState.value.copy(logo = bmp.toString())
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    /**
     * Uploads image to firebase storage
     * @param image takes in a image Bitmap
     * @param userId the users id, this is used to name the image
     */
    fun upLoadImageToFireBase(image: Bitmap, userId: String) {
        // Inside your Composable function
        val storageRef = Firebase.storage.reference

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // sets loading to true
                _uiState.value = _uiState.value.copy(isLoading = true)

                val imageName = "${userId}.jpg"
                val imageRef = storageRef.child("logos/${imageName}")

                val stream = ByteArrayOutputStream()
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream)

                val data = stream.toByteArray()

//                // Convert ByteArray to Base64 string before storing
//                val base64Logo = Base64.encodeToString(data, Base64.DEFAULT)
//
//                // Retrieve and decode from Base64 when reading from Firestore
//                val logoByteArray = Base64.decode(base64Logo, Base64.DEFAULT)

                val uploadTask = imageRef.putBytes(data)

                // Assuming you are inside a coroutine scope or function
                val downloadUri = try {
                    val downloadUri = uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            throw task.exception ?: Exception("Image upload failed")
                        }
                        // Continue with the task to get the download URL
                        imageRef.downloadUrl

                    }.await() // Call await() within a coroutine

                    downloadUri // Assign to downloadUri
                } catch (exception: Exception) {
                    // Handle errors here, log or display an error message
                    Log.e("Business Account Update", "Error: ${exception.message}", exception)
                    // Show an error message or update UI accordingly
                    _uiState.value = _uiState.value.copy(
                        hasMessage = true, message = "An error occurred"
                    )
                    null // Assign null to downloadUri in case of an error
                }

                uploadTask.addOnSuccessListener {
                    SnackbarManager.showMessage(R.string.image_uploaded_successfully)
                }.addOnFailureListener {
                    // Handle upload failure
                    SnackbarManager.showMessage(R.string.something_went_wrong)
                }

                _uiState.value = _uiState.value.copy(logo = downloadUri.toString())
            }
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }

    fun getAllCategories() = localOfflineDatabase.categoryDao().getAllCategories()

    private fun getCategory(name: String): Category {
        return Category(name = name)
    }
}