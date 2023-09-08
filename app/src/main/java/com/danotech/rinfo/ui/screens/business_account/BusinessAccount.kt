package com.danotech.rinfo.ui.screens.business_account

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.imageLoader
import com.danotech.rinfo.R
import com.danotech.rinfo.common.ext.basicButton
import com.danotech.rinfo.ui.components.BusinessAccountButton
import com.danotech.rinfo.ui.components.Loading
import com.danotech.rinfo.ui.components.ProfileImageShimmer
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.TextInputWithLabel
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.dropdown.DropdownViewModel
import com.danotech.rinfo.ui.screens.dropdown.SelectBusinessCategory
import com.google.firebase.auth.FirebaseAuth
import com.togitech.ccp.component.TogiCountryCodePicker
import com.togitech.ccp.data.utils.checkPhoneNumber
import com.togitech.ccp.data.utils.getDefaultLangCode
import com.togitech.ccp.data.utils.getDefaultPhoneCode
import com.togitech.ccp.data.utils.getLibCountries

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessAccount(
    modifier: Modifier = Modifier,
    viewModel: BusinessAccountViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
) {
    val openAlertDialog = remember {
        mutableStateOf(false)
    }

    BackHandler {
        onBackClicked()
    }

    val context = LocalContext.current

    val defaultProfilePicture: Bitmap = BitmapFactory.decodeResource(
        context.resources, R.drawable.no_image
    )

    LaunchedEffect(viewModel) {
        viewModel.getBusinessAccount(FirebaseAuth.getInstance().currentUser?.email!!)
    }

    val uiState = viewModel.uiState.collectAsState().value

    var showCategorySelection by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    rememberCoroutineScope()


    val logoImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.no_image)

    val bitmap = remember {
        mutableStateOf(logoImage)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        if (it != null) {
            bitmap.value = it
        }
    }

    // getting an image from the gallery
    val launchImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
        } else {
            val source = it?.let { it1 ->
                ImageDecoder.createSource(context.contentResolver, it1)
            }

            bitmap.value = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }!!
        }
    }

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

            // if showCategorySelection false show Main page
            // Otherwise show BusinessActionSectionPage
            if (!showCategorySelection) {
                BusinessAccountContent(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                    bitmap = bitmap,
                    onNextButtonClick = {
                        showCategorySelection = true
                    })
            } else {
                BusinessActionSectionPage(
                    uiState = uiState,
                    bitmap = bitmap,
                    viewModel = viewModel,
                    onBackClicked = {
                        showCategorySelection = false
                    },
                    onSave = {
                        openAlertDialog.value = true
                    },
                    onFailure = {
                        uiState.message = it.toString()
                    },
                    innerPadding = innerPadding
                )
            }

            if (openAlertDialog.value) AccountDialog(
                onDismissRequest = { openAlertDialog.value = false },
                dialogTitle = "Information added successfully",
                dialogText = uiState.name,
                icon = Icons.Default.ThumbUp
            )


            if (uiState.showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        viewModel.openBottomSheet(false)
                    }, sheetState = sheetState
                ) {
                    BottomSheetAddImage(onAddImageClick = {
                        launchImage.launch("image/*")
                    }, onCameraImageAddClick = {
                        launcher.launch()
                    })
                }
            }
        } else {
            Loading()
        }
    }
}

@Composable
fun BottomSheetAddImage(
    onAddImageClick: () -> Unit, onCameraImageAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                contentDescription = "upload image",
                alignment = Alignment.BottomEnd,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(5.dp)
                    .clickable {
                        onCameraImageAddClick()
                    })

            Text(
                text = "Camera",
                style = MaterialTheme.typography.titleMedium,

                )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(painter = painterResource(id = R.drawable.baseline_image_24),
                contentDescription = "upload image",
                alignment = Alignment.BottomEnd,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(5.dp)
                    .clickable {
                        onAddImageClick()
                    })

            Text(
                text = "Gallery", style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun BusinessAccountContent(
    uiState: BusinessAccountUiState,
    bitmap: MutableState<Bitmap>,
    viewModel: BusinessAccountViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    dropdownViewModel: DropdownViewModel = hiltViewModel(),
    onNextButtonClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val imageSize = 150.dp
                ProfileImageShimmer(
                    size = imageSize, isLoading = uiState.imageLoading
                ) {
                    val borderWidth = 1.dp
                    // Load the image using AsyncImage and the custom ImageLoader
                    AsyncImage(
                        model = uiState.logo,
                        contentDescription = "logo of ${uiState.name}", // Provide a content description
                        imageLoader = LocalContext.current.imageLoader,
                        modifier = Modifier
                            .fillMaxSize()
                            .size(imageSize)
                            .clip(CircleShape)
                    )
                }

                Row(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box {
                        Image(painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                            contentDescription = "upload image",
                            alignment = Alignment.BottomEnd,
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                                MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(20.dp)
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(5.dp)
                                .clickable {
                                    showDialog = true
                                    viewModel.openBottomSheet(true)
                                })
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            AnimatedVisibility(visible = uiState.hasMessage) {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

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
            RinfoButton(
                name = R.string.next_page,
                modifier = Modifier.fillMaxWidth(),
                onClicked = onNextButtonClick
            )
        }
    }
}

/**
 * This page just shows the Business Account page
 * Let's the user select a category
 * This might change in the future to add more information
 */
@Composable
fun BusinessActionSectionPage(
    uiState: BusinessAccountUiState,
    bitmap: MutableState<Bitmap>,
    viewModel: BusinessAccountViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    dropdownViewModel: DropdownViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onSave: () -> Unit,
    onFailure: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(dimensionResource(id = R.dimen.body_padding)),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AnimatedVisibility(visible = uiState.hasMessage) {
            Text(
                text = uiState.message,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
            )
        }

        SelectBusinessCategory(
            selected = uiState.businessCategory, onCategorySelected = viewModel::onCategoryChange
        )

        BusinessAccountButton(
            isLoading = uiState.isLoading, modifier = Modifier
                .fillMaxWidth()
                .basicButton()
        ) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                viewModel.upLoadImageToFireBase(bitmap.value, currentUser.email!!)
            }
            viewModel.onBusinessAccountCreated(onSuccess = onSave, onFailure = {
                onFailure(it)
            })
            onSave()
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