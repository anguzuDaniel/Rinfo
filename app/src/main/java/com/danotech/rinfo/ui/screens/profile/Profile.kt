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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading
import com.danotech.rinfo.ui.components.ProfileButton
import com.danotech.rinfo.ui.components.ProfileImage
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
                        Text("Hide bottom sheet")
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
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding,
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
                            image = uiState.profileImage,
                            size = imageSize,
                            context = context
                        )
                    }
                }
                Text("Add photo")
            }
            Spacer(modifier = Modifier.height(40.dp))
            HorizontalDivider()
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