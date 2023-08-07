package com.danotech.rinfo.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R

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
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.LightGray)
            .shadow(elevation = 4.dp, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageUrI),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
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
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        )
        Text(
            text = "OR", modifier = Modifier.padding(10.dp)
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
        )
    }
}