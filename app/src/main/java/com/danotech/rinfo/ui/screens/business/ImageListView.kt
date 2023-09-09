@file:Suppress("DEPRECATION")

package com.danotech.rinfo.ui.screens.business

import android.graphics.Bitmap
import android.view.Window
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.ui.ThemeViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.launch
import kotlin.math.min


/**
 * Image list that use a list if ImageItems to display images in as HorizontalPager
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageListViewImageItem(
    imageList: List<ImageItem>,
    scrollState: ScrollState,
    size: Dp
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })
    val parallaxColor by remember { mutableStateOf(Color.Transparent) }

    /**
     * Scrollable functionality of the image
     */
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        pageSize = PageSize.Fill,
    ) { index ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            Image(
                bitmap = imageList[index].bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(parallaxColor)
                    .graphicsLayer { alpha = min(1f, 1 - (scrollState.value / 400f)) }
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)),
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .size(size),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Indicators
                DotsIndicator(
                    totalDots = imageList.size,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unSelectedColor = Color.White,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageListViewBitmap(
    imageList: List<Bitmap>,
    scrollState: ScrollState,
    size: Dp,
    themeViewModel: ThemeViewModel = hiltViewModel(),
    window: Window
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { imageList.size })
    var parallaxColor by remember { mutableStateOf(Color.Transparent) }

    val view = LocalView.current
    val windowInsetsController =
        WindowCompat.getInsetsController(window, view)


    // Swipe functionality of the image
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize(),
        pageSize = PageSize.Fill,
        verticalAlignment = Alignment.CenterVertically,
    ) { index ->
        Box {
            val bitmap = imageList[index].asImageBitmap()

            // sets the color of the status bar
            // according to the image shown currently
            SideEffect {
                coroutineScope.launch {
                    val (color, isLight) = bitmap.asAndroidBitmap().computeDominantTopSectionColor()
                    parallaxColor = color
                    windowInsetsController.isAppearanceLightStatusBars = isLight
                    window.statusBarColor = color.toArgb() ?: Color.DarkGray.toArgb()
                }
            }

            Image(
                bitmap = bitmap,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .background(parallaxColor)
                    .graphicsLayer { alpha = min(1f, 1 - (scrollState.value / 400f)) },
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .size(size),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                // Indicators
                DotsIndicator(
                    totalDots = imageList.size,
                    selectedIndex = pagerState.currentPage,
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unSelectedColor = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            // Customize the status bar color within this AppBar
            DisposableEffect(Unit) {
                onDispose {
                    // Reset the status bar color when this composable is disposed
                    val useDarkIcons = themeViewModel.themeState.value.isDarkMode

                    windowInsetsController.isAppearanceLightStatusBars = !useDarkIcons
                    window.statusBarColor = Color.Transparent.toArgb()
                }
            }
        }
    }
}