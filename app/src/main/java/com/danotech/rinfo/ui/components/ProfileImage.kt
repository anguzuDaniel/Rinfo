package com.danotech.rinfo.ui.components

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Profile image
 * @param size
 * @param onProfileImageClick
 *
 * reuseable profile image
 */
@Composable
fun ProfileImage(
    size: Dp,
    @DrawableRes imageUrI: Int,
    onProfileImageClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val borderWidth = 1.dp
    Image(
        painter = painterResource(id = imageUrI),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}

@Composable
fun ProfileImageBitmap(
    size: Dp,
    bitmap: Bitmap,
    onProfileImageClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val borderWidth = 1.dp
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}

@Composable
fun AddProfileImage(
    imageUrI: ImageBitmap,
    size: Dp,
    onProfileImageClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val borderWidth = 1.dp
    Image(
        bitmap = imageUrI,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {
        Button(onClick = onProfileImageClick) {
            Text(text = "Upload Image")
        }
    }
}

@Composable
fun OrFormDiver() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        )
        Text(
            text = "OR", modifier = Modifier.padding(10.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        )
    }
}