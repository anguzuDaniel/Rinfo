package com.danotech.rinfo.ui.screens.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading
import com.danotech.rinfo.ui.components.ProfileButton
import com.danotech.rinfo.ui.components.TextInputWithLabel
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.business_account.BottomSheetAddImage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    BackHandler {
        onBackClicked()
    }

    LaunchedEffect(viewModel) {
        viewModel.getImageFromFireBase(FirebaseAuth.getInstance().currentUser?.email!!)
        viewModel.getProfile()
    }

    val uiState = viewModel.uiState.collectAsState().value

    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

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
        modifier = modifier
            .fillMaxSize(),
        topBar = if (!uiState.isLoading) {
            {
                RinfoTopAppBar(
                    title = "Profile",
                    isShowingHomePage = false,
                    onBackButtonClicked = onBackClicked,
                )
            }
        } else {
            {}
        },
    ) { innerPadding ->
        if (!uiState.isLoading) {
            ProfileContent(
                innerPadding = innerPadding,
                viewModel = viewModel,
                bitmap = bitmap
            )

            if (uiState.showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        viewModel.openBottomSheet(false)
                    },
                    sheetState = sheetState
                ) {
                    BottomSheetAddImage(
                        onAddImageClick = {
                            launchImage.launch("image/*")
                        },
                        onCameraImageAddClick = {
                            launcher.launch()
                        }
                    )
                    // Sheet content
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                viewModel.openBottomSheet(false)
                            }
                        }
                    }) {
                        androidx.compose.material3.Text("Hide bottom sheet")
                    }
                }
            }
        } else {
            Loading()
        }
    }
}

@Composable
fun ProfileContent(
    bitmap: MutableState<Bitmap>,
    viewModel: ProfileViewModel,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState().value
    var profilePic: Bitmap = uiState.profileImageBitmap


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding,
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    val borderWidth = 1.dp
                    Image(
                        bitmap = bitmap.value.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                            contentDescription = "upload image",
                            alignment = Alignment.BottomEnd,
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(24.dp)
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(5.dp)
                                .clickable {
                                    viewModel.openBottomSheet(true)
                                }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Divider()
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileName,
                onValueChanged = viewModel::onProfileNameChanged,
                labelText = stringResource(R.string.profile_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileFirstName,
                onValueChanged = viewModel::onProfileFirstNameChanged,
                labelText = stringResource(R.string.first_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            TextInputWithLabel(
                value = uiState.profileLastName,
                onValueChanged = viewModel::profileLastNameChanged,
                labelText = stringResource(R.string.last_name)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            ProfileButton(
                modifier = Modifier
                    .fillMaxWidth(),
                isLoading = uiState.isLoading,
            ) {
                viewModel.saveProfile()
            }
        }
    }
}