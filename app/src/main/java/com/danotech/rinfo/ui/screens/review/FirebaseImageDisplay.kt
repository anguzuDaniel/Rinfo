package com.danotech.rinfo.ui.screens.review

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danotech.rinfo.R

/**
 * Used to show the images from firebase
 * @param imageSize image size in Dp
 * @param url the url of the image you want to display
 * @param description the image description
 * @param shape the shape you want the image to be - should be of typ CornerBasedShape
 */
@Composable
fun FirebaseImageDisplay(
    url: String,
    description: String,
    modifier: Modifier = Modifier,
    imageSize: Dp = 0.dp,
    isFullScreen: Boolean = false,
    shape: CornerBasedShape = RoundedCornerShape(0.dp),
) {
    Column(
        modifier = if (!isFullScreen)
            modifier.size(imageSize)
        else
            modifier
                .size(imageSize)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .listener(
                    onError = { _, result ->
                        println(result.throwable)
                        Log.d(TAG, "FIREBASE IMAGE:------> ${result.throwable}")
                    },
                )
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = description,
            error = painterResource(R.drawable.profile_placeholder),
            placeholder = painterResource(R.drawable.loading_img),
            modifier = Modifier
                .clip(shape)
                .aspectRatio(1f),
            contentScale = if (!isFullScreen) ContentScale.Crop else ContentScale.FillWidth
        )
    }
}