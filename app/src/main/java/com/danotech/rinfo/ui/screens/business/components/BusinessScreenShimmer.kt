package com.danotech.rinfo.ui.screens.business.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.shimmerEffect

@Composable
fun BusinessScreenShimmer(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        Box(
            modifier = modifier
        ) {
            LazyColumn(
                modifier = modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                )
            ) {
                item {
                    val size = 300.dp
                    Box(
                        modifier = modifier
                            .height(size)
                            .fillMaxWidth()
                            .shimmerEffect(),
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.body_padding)),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .shimmerEffect()
                            )

                            Box(
                                modifier = Modifier.shimmerEffect()
                            )

                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(200.dp, 10.dp)
                                    .shimmerEffect()
                            )

                            Box(
                                modifier = Modifier
                                    .size(200.dp, 10.dp)
                                    .shimmerEffect()
                            )

                            Box(
                                modifier = Modifier
                                    .size(200.dp, 10.dp)
                                    .shimmerEffect()
                            )
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(
                                vertical = 8.dp,
                                horizontal = dimensionResource(id = R.dimen.body_padding)
                            )
                            .fillMaxWidth(),
                    ) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(shape = MaterialTheme.shapes.medium)
                                        .shimmerEffect()
                                )
                            }

                            item {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(shape = MaterialTheme.shapes.medium)
                                        .shimmerEffect()
                                )
                            }

                            item {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(shape = MaterialTheme.shapes.medium)
                                        .shimmerEffect()
                                )
                            }

                            item {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(shape = MaterialTheme.shapes.medium)
                                        .shimmerEffect()
                                )
                            }

                            item {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(shape = MaterialTheme.shapes.medium)
                                        .shimmerEffect()
                                )
                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.body_padding))
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(200.dp, 10.dp)
                                .shimmerEffect()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(300.dp, 100.dp)
                                .shimmerEffect()
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(
                                vertical = 8.dp,
                                horizontal = dimensionResource(id = R.dimen.body_padding)
                            )
                            .fillMaxWidth(),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(200.dp, 20.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .shimmerEffect(),
                        )
                    }
                }
            }
        }
    } else {
        contentAfterLoading()
    }
}

@Composable
fun BusinessImageShimmer(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {
    if (isLoading) {
        val size = 300.dp
        Box(
            modifier = modifier
                .height(size)
                .fillMaxWidth()
                .shimmerEffect(),
        )
    } else {
        contentAfterLoading()
    }
}