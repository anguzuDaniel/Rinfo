package com.danotech.rinfo.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun BusinessCardShimmer(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp,
            ),
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement
                    .spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier.clip(MaterialTheme.shapes.small)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .shimmerEffect()
                    )
                }

                Column(
                    modifier = Modifier
                        .size(100.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .shimmerEffect()
                    )

                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .shimmerEffect()
                    )

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .shimmerEffect()
                    )
                }
            }
        }
    } else {
        contentAfterLoading()
    }
}


@Composable
fun BusinessImageShimmer(
    size: Dp,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .clip(MaterialTheme.shapes.small)
                .size(size)
                .shimmerEffect()
        )
    } else {
        contentAfterLoading()
    }
}

@Composable
fun ProfileImageShimmer(
    size: Dp,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(size)
                .shimmerEffect()
        )
    } else {
        contentAfterLoading()
    }
}


fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFBBB5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

@Composable
fun BusinessGalleryRowShimmer(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    val imageSize = 150.dp
    val h = 20.dp
    val w = 200.dp

    if (isLoading) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 16.dp)
                .clickable {},
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Box(
                modifier = modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(imageSize)
                    .shimmerEffect()
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = modifier
                        .clip(MaterialTheme.shapes.medium)
                        .size(width = w, height = h)
                        .shimmerEffect()
                )
                Box(
                    modifier = modifier
                        .clip(MaterialTheme.shapes.medium)
                        .size(width = w, height = h)
                        .shimmerEffect()
                )
            }
        }
    } else {
        contentAfterLoading()
    }
}
