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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading
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

