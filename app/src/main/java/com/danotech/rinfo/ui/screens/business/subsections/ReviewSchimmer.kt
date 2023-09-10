package com.danotech.rinfo.ui.screens.business.subsections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.components.shimmerEffect

@Composable
fun ReviewShimmer(
    modifier: Modifier = Modifier,
    times: Int = 3,
    isLoading: Boolean,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    contentAfterLoading: @Composable () -> Unit, // should always be the last, DO NOT REMOVE
) {
    val imageSize = 35.dp

    if (isLoading) {
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            repeat(times) {
                Row(
                    modifier = modifier
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Box(
                        modifier = modifier
                            .clip(CircleShape)
                            .size(imageSize)
                            .shimmerEffect()
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(500.dp, 5.dp)
                                .shimmerEffect()
                        )

                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .size(500.dp, 5.dp)
                                .shimmerEffect()
                        )

                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .size(500.dp, 100.dp)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    } else {
        contentAfterLoading()
    }
}