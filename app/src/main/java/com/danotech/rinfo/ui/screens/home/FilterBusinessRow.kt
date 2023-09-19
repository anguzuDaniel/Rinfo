package com.danotech.rinfo.ui.screens.home

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.FilterBusinessButton
import com.danotech.rinfo.ui.screens.review.FirebaseImageDisplay

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
    val filterOptions = listOf(
        "Popular",
        "Latest",
        "Trending",
        "Affordable",
        "Nearby",
        "Recommended",
        "Top-rated",
        "Newly Opened",
        "Family-friendly",
        "Pet-friendly",
        "Healthy Choices",
        "Local Favorites",
        "Cuisine-Specific",
        "Outdoor Seating",
        "Delivery Available",
        "Vegetarian/Vegan",
        "Gluten-Free",
        "Happy Hour",
        "Live Music",
        "Wi-Fi Hotspot",
        "Late-night Eats",
        "Food Trucks",
        "Artisanal",
        "Romantic",
        "Historic Locations",
        "Adventure Dining",
        "International Flavors",
        "Cozy Cafes",
        "Craft Breweries",
        "Dessert Delights",
        "Hidden Gems",
        "Budget-Friendly",
        "Luxury Dining"
    )

    val clicked by remember { mutableStateOf(false) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = paddingStart),
                top = dimensionResource(id = paddingHorizontal),
            ),
        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(filterOptions) { item ->
            FilterBusinessButton(
                name = item,
                active = clicked,
                onFilterClick = onFilterClicked
            )
        }
    }
}

@Composable
fun BusinessSnippetRow(
    businessList: List<Business>,
    @DimenRes paddingStart: Int,
    @DimenRes paddingHorizontal: Int
) {
    val imageSize = 80.dp

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = paddingStart),
            ),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(businessList) { business ->
            FirebaseImageDisplay(
                imageSize = imageSize,
                url = business.logo,
                description = "${business.name} logo",
                shape = CircleShape
            )
        }
    }
}
