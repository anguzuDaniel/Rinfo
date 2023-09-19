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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Loading
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar

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
        viewModel.getBusinessAccount()
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