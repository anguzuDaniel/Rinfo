package com.danotech.rinfo.ui.screens.business_account

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading
import com.danotech.rinfo.ui.components.ProfileImage
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.TextInputWithLabel
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.google.firebase.auth.FirebaseAuth

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
                    viewModel = viewModel,
                    innerPadding = innerPadding,
                    context = context
                ) {
                    showCategorySelection = true
                }
            } else {
                BusinessActionSectionPage(
                    uiState = uiState,
                    bitmap = bitmap,
                    viewModel = viewModel,
                    innerPadding = innerPadding,
                    onSave = {
                        openAlertDialog.value = true
                    }
                ) {
                    uiState.message = it
                }
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
    context: Context,
    uiState: BusinessAccountUiState,
    viewModel: BusinessAccountViewModel,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onNextButtonClick: () -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = modifier.padding(dimensionResource(id = R.dimen.body_padding)),
            contentPadding = innerPadding
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val imageSize = 150.dp
                    Surface(
                        onClick = {
                            showDialog = true
                            viewModel.openBottomSheet(true)
                        },
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    ) {
                        Box(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.profile_image_size_large)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CameraAlt,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Box(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.profile_image_size_large)),
                            contentAlignment = Alignment.Center
                        ) {
                            ProfileImage(
                                image = uiState.logo,
                                size = imageSize,
                                context = context
                            )
                        }
                    }
                    Text("Add photo")
                }
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
}