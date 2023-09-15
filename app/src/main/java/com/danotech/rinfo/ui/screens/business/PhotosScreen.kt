package com.danotech.rinfo.ui.screens.business

import android.graphics.Bitmap
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.ui.components.PhotoGrid
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.business.components.downloadImages

@Composable
fun PhotosScreen(
    businessId: String,
    viewModel: BusinessViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    window: Window,
) {
    BackHandler {
        onBackClick()
    }

    val downloadedImages = remember { mutableStateListOf<Bitmap>() }

    LaunchedEffect(key1 = Unit) {
        downloadImages(
            businessId = businessId,
            startIndex = 0,
            onSuccess = { _, bitmap ->
                // Convert the downloaded bitmap to a Composable Painter
                downloadedImages.add(bitmap)
            },
            onError = { index, exception ->
                // Handle error for image at index
                println("Error downloading image at index $index: ${exception.message}")
            }
        )
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = false,
                showBackgroundColor = false,
                onBackButtonClicked = onBackClick,
                actions = {},
                title = "Uploads",
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        PhotoGrid(
            photos = downloadedImages,
            minSize = 150.dp,
            loading = uiState.isLoading,
            modifier = Modifier
                .fillMaxSize(),
            innerPadding = it
        )
    }
}