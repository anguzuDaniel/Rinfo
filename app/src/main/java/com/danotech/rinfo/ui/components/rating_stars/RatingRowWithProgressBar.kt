package com.danotech.rinfo.ui.components.rating_stars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.screens.business.components.LinearProgressBar

@Composable
fun RatingRowWithProgressBar(
    rating: Int,
    currentRating: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$rating", fontWeight = FontWeight.Bold)
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        LinearProgressBar(
            progress = currentRating.toFloat() / 5,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}