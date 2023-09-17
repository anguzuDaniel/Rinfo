package com.danotech.rinfo.ui.screens.home

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.FilterBusinessButton

/**
 * Show filter options
 * @param onFilterClicked function called when FilterBusinessButton is clicked
 */
@Composable
fun FilterBusinessRow(
    onFilterClicked: () -> Unit = {},
    @DimenRes paddingStart: Int,
    @DimenRes paddingHorizontal: Int
) {
    val clicked by remember { mutableStateOf(false) }

    LazyRow(
        modifier = Modifier.padding(
            start = dimensionResource(id = paddingStart),
            top = dimensionResource(id = paddingHorizontal),
        ),
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        item {
            FilterBusinessButton(
                name = R.string.popular,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.latest,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.trending,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.affordable,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.popular,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.latest,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.trending,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.affordable,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.affordable,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.popular,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.latest,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.trending,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }

        item {
            FilterBusinessButton(
                name = R.string.affordable,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }
    }
}