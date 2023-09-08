package com.danotech.rinfo.ui.screens.business_account

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedTab(
    items: List<String>,
    modifier: Modifier,
    indicatorPadding: Dp = 4.dp,
    selectedItemIndex: Int = 0,
    onSelectedTab: (index: Int) -> Unit
) {

    var tabWidth by remember { mutableStateOf(0.dp) }

    val indicatorOffset: Dp by animateDpAsState(
        if (selectedItemIndex == 0) {
            tabWidth * (selectedItemIndex / items.size.toFloat())
        } else {
            tabWidth * (selectedItemIndex / items.size.toFloat()) - indicatorPadding
        }
    )

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                tabWidth = coordinates.size.width.dp
            }
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
    ) {

        MyTabIndicator(
            modifier = Modifier
                .padding(indicatorPadding)
                .fillMaxHeight()
                .width(tabWidth / items.size - indicatorPadding),
            indicatorOffset = indicatorOffset
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, title ->
                MyTabItem(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(tabWidth / items.size),
                    onClick = {
                        onSelectedTab(index)
                    },
                    title = title
                )
            }
        }

    }
}

@Composable
private fun MyTabIndicator(
    modifier: Modifier,
    indicatorOffset: Dp,
) {
    Box(
        modifier = modifier
            .offset(x = indicatorOffset)
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colorScheme.background)
    )
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun MyTabItem(
    modifier: Modifier,
    onClick: () -> Unit,
    title: String
) {
    val interactionSource = MutableInteractionSource()
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = title)
    }
}