package com.danotech.rinfo.ui.screens.business.subsections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(rating: Int) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = null,
                tint = Color(0xFFFFD700), // Gold color for filled stars
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun RatingRow(rating: Int, currentRating: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = "$rating star${if (rating > 1) "s" else ""}: ", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(if (rating > 1) 8.dp else 9.dp))
        LinearProgressBar(
            progress = currentRating.toFloat() / 5,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Color.Gray)
        )
    }
}