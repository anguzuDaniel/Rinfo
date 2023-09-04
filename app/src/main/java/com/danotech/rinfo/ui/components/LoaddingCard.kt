package com.danotech.rinfo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.theme.AppTheme

@Composable
fun LoadingCard(
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement
                .spacedBy(10.dp)
        ) {

            PlaceholderImage(modifier = Modifier.size(100.dp))

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                PlaceholderText(modifier = Modifier.width(150.dp))
                // Placeholder content for the loading card
                Spacer(modifier = Modifier.height(16.dp))
                PlaceholderText(modifier = Modifier.width(150.dp))
                Spacer(modifier = Modifier.height(8.dp))
                PlaceholderText(modifier = Modifier.width(100.dp))
            }
        }

    }
}

@Composable
fun PlaceholderImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.small)
            .background(Color.Gray)
    )
}

@Composable
fun PlaceholderText(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(Color.Gray)
    )
}

@Preview
@Composable
fun LoadingCardPreview() {
    AppTheme() {
        LoadingCard()
    }
}
