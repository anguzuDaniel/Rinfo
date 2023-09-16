package com.danotech.rinfo.ui.components

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danotech.rinfo.R

/**
 * UI state for the Home screen
 */
sealed interface PhotoUiState {
    data class Success(val photos: List<Bitmap>) : PhotoUiState
    object Error : PhotoUiState
    object Loading : PhotoUiState
}

@Composable
fun ProfileImage(
    image: String,
    size: Dp,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    context: Context
) {
    ProfileImageShimmer(
        size = size, isLoading = loading
    ) {
        // Load the image using AsyncImage and the custom ImageLoader
        AsyncImage(
            model = image,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .size(size)
        )
    }
}

@Composable
fun PhotoItemBitmap(
    image: Bitmap,
    size: Dp,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    context: Context
) {
    ProfileImageShimmer(
        size = size, isLoading = loading
    ) {
        // Load the image using AsyncImage and the custom ImageLoader
        AsyncImage(
            model = image,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(size)
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Composable
fun PhotoGrid(
    loading: Boolean,
    minSize: Dp,
    photos: List<Bitmap>,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = innerPadding
    ) {
        items(photos) { photo ->
            PhotoItemBitmap(
                image = photo,
                size = minSize,
                loading = loading,
                context = LocalContext.current
            )
        }
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_broken_image),
            contentDescription = null
        )
        Text(stringResource(R.string.failed_to_load_images))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}