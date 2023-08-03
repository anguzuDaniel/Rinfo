package com.danotech.rinfo.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.category.Category

object LocalReviewProvider {
    val defaultReview = Review(
        id = 1,
        avatarResource = R.drawable.baseline_person_24,
        imageUrl = R.drawable.cafe_javas,
        businessName = "Cafe Javas",
        rating = 4,
        comment = "This place has the best coffee and sandwiches in town!"
    )

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

    val categories = listOf(
        Category("Hotels", Icons.Default.Home),
        Category("Restaurants", Icons.Default.Home),
        Category("Cafes", Icons.Default.Home),
        Category("Spas", Icons.Default.Home),
        Category("Parks", Icons.Default.Home),
        Category("Museums", Icons.Default.Home),
        Category("Fitness Centers", Icons.Default.Home),
        Category("Bookstores", Icons.Default.Home),
        Category("Beaches", Icons.Default.Home),
        Category("Movie Theaters", Icons.Default.Home),
    )
}