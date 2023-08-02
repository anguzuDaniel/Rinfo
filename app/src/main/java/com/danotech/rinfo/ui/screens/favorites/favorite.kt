package com.danotech.rinfo.ui.screens.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.components.ReviewCard
import com.example.compose.AppTheme

@Composable
fun FavoriteScreen(
    onBackPressed: () -> Unit = {}
) {
    val reviews = listOf(
        Review(
            id = 1,
            avatarResource = R.drawable.baseline_person_24,
            imageUrl = R.drawable.cafe_javas,
            businessName = "Cafe Javas",
            rating = 4,
            comment = "This place has the best coffee and sandwiches in town!"
        ),
        Review(
            id = 2,
            avatarResource = R.drawable.baseline_person_24,
            imageUrl = R.drawable.kfc,
            businessName = "KFC",
            rating = 3,
            comment = "This kitchen place in town!"
        )
    )

    LazyColumn(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.body_padding))
            .fillMaxSize()
    ) {
        items(reviews, key = { review -> review.id }) { review ->
            ReviewCard(review = review)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    AppTheme {
        FavoriteScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        FavoriteScreen()
    }
}