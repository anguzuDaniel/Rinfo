package com.danotech.rinfo.ui.screens.business_account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R

@Composable
fun BottomSheetAddImage(
    onAddImageClick: () -> Unit,
    onCameraImageAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                contentDescription = "upload image",
                alignment = Alignment.BottomEnd,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(5.dp)
                    .clickable {
                        onCameraImageAddClick()
                    })

            Text(
                text = "Camera",
                style = MaterialTheme.typography.titleMedium,

                )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Image(painter = painterResource(id = R.drawable.baseline_image_24),
                contentDescription = "upload image",
                alignment = Alignment.BottomEnd,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(50.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(5.dp)
                    .clickable {
                        onAddImageClick()
                    })

            Text(
                text = "Gallery", style = MaterialTheme.typography.titleMedium
            )
        }
    }
}