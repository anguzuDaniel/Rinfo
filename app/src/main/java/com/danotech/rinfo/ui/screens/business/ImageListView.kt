package com.danotech.rinfo.ui.screens.business

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageListViewImageItem(imageList: List<ImageItem>) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    /**
     * Scrollable functionality of the image
     */
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        pageSize = PageSize.Fill
    ) { index ->
        Box {
            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            // Our index content
            Text(
                text = "Image: $index",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.body_padding))
            )

            // Indicators
            DotsIndicator(
                totalDots = imageList.size,
                selectedIndex = pagerState.currentPage,
                selectedColor = MaterialTheme.colorScheme.primary,
                unSelectedColor = MaterialTheme.colorScheme.surface
            )

            Image(
                bitmap = imageList[index].bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageListViewBitmap(imageList: List<Bitmap>) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    /**
     * Scrollable functionality of the image
     */
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        pageSize = PageSize.Fill
    ) { index ->
        Box {
            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            // Our index content
            Text(
                text = "Image: $index",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.body_padding))
            )

            // Indicators
            DotsIndicator(
                totalDots = imageList.size,
                selectedIndex = pagerState.currentPage,
                selectedColor = MaterialTheme.colorScheme.primary,
                unSelectedColor = MaterialTheme.colorScheme.surface
            )

            Image(
                bitmap = imageList[index].asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ImageListItem(
    bitmap: Bitmap
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(250.dp)
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}