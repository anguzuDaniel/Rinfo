package com.danotech.rinfo.ui.screens.selected_category

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SelectedCategoryScreen(
    viewModel: SelectedCategoryViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {}
) {
    BackHandler {
        onBackPressed()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SelectedSearchBar(
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                onBack = onBackPressed,
                viewModel = viewModel,
            )

            SearchCategoryContent(viewModel = viewModel)
        }
    }
}

@Composable
fun SearchCategoryContent(
    modifier: Modifier = Modifier,
    viewModel: SelectedCategoryViewModel,
    searchQuery: String = "",
    onMapClicked: () -> Unit = {},
    onBackPressed: () -> Unit = {},
) {
    val uiState = viewModel.uiState.value

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface),
    ) {
        item {

        }

        item {
            val businessList = createBusinessList()
            BusinessInLocation(
                businessList = businessList
            )
        }
    }
}

@Composable
fun MapDisplay(
    modifier: Modifier = Modifier,
    mapUrl: String,
    onMapClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .size(400.dp)
            .clickable(onClick = onMapClicked)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {

    }
}

data class Business(
    val name: String,
    val address: String,
    val phone: String,
    val website: String,
    val category: String,
    val rating: Float,
    val reviewCount: Int,
    val price: String,
    val imageUrl: String,
    val distance: Float,
    val latitude: Float,
    val longitude: Float
)

@Composable
fun BusinessList(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(contentPadding = innerPadding) {
        item {
            val businessList = createBusinessList()
            BusinessInLocation(businessList = businessList)
        }
    }
}

@Composable
fun BusinessCard(business: Business, modifier: Modifier) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = modifier
            .fillMaxWidth()
            .width(300.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.cafe_javas),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
            )

            Column {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = business.address,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                RatingStars(
                    rating = business.reviewCount,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "location"
                    )


                    Text(
                        text = "${business.distance} km",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

            }
        }
    }

    Spacer(modifier = Modifier.width(16.dp))
}


fun createBusinessList(): List<Business> {
    val businessList = mutableListOf<Business>()

    businessList.add(
        Business(
            "Restaurant A",
            "123 Main St",
            "(123) 456-7890",
            "www.restaurants.com",
            "Restaurant",
            4.5f,
            3,
            "$$$",
            "https://example.com/restaurant_a.jpg",
            2.3f,
            37.12345f,
            -122.54321f
        )
    )

    businessList.add(
        Business(
            "Cafe B",
            "456 Park Ave",
            "(987) 654-3210",
            "www.cafeb.com",
            "Cafe",
            4.0f,
            3,
            "$$",
            "https://example.com/cafe_b.jpg",
            1.5f,
            37.54321f,
            -122.98765f
        )
    )

    // Add more businesses here...

    return businessList
}

@Composable
fun BusinessInLocation(
    businessList: List<Business>,
    modifier: Modifier = Modifier
) {
    val padding = 20.dp
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = padding,
                top = padding,
                bottom = padding
            )
    ) {
        items(businessList.size) { index ->
            BusinessCard(
                business = businessList[index],
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun SearchCategoryPreview() {
    AppTheme {
        val businessList = createBusinessList()
        BusinessInLocation(businessList = businessList)
    }
}

@Preview
@Composable
fun SearchCategoryLightPreview() {
    AppTheme {
        SelectedCategoryScreen()
    }
}

@Preview
@Composable
fun SearchCategoryDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        SelectedCategoryScreen()
    }
}