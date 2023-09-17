package com.danotech.rinfo.ui.screens.review

import android.content.ContentValues.TAG
import android.graphics.drawable.shapes.Shape
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danotech.rinfo.R

@Composable
fun BusinessCardImage(
    imageSize: Dp,
    url: String,
    description: String,
    shape: CornerBasedShape
) {
    Column(
        modifier = Modifier.size(imageSize)
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
                .data(url) //👈 Doesn't work!!!
                .crossfade(true)
                .build(),
            contentDescription = description,
            error = painterResource(R.drawable.no_image),
            placeholder = painterResource(R.drawable.loading_img),
            modifier = Modifier
                .clip(shape)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
        )
    }
}