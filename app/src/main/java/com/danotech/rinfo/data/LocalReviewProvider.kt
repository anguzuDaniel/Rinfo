package com.danotech.rinfo.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BeachAccess
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.Theaters
import androidx.compose.ui.res.stringResource
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.account.AccountType
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
        Category("Hotels", Icons.Default.Hotel, R.drawable.baseline_local_hotel_24),
        Category("Restaurants", Icons.Default.Restaurant, R.drawable.baseline_restaurant_24),
        Category("Cafes", Icons.Default.LocalCafe, R.drawable.baseline_coffee_24),
        Category("Spas", Icons.Default.Spa, R.drawable.baseline_spa_24),
        Category(
            "Parks",
            Icons.Default.Park,
            R.drawable.baseline_spa_24
        ),
        Category("Museums", Icons.Default.Museum, R.drawable.baseline_museum_24),
        Category(
            "Fitness Centers",
            Icons.Default.SportsGymnastics,
            R.drawable.baseline_fitness_center_24
        ),
        Category("Bookstores", Icons.Default.LibraryBooks, R.drawable.baseline_library_books_24),
        Category("Beaches", Icons.Default.BeachAccess, R.drawable.baseline_beach_access_24),
        Category("Movie Theaters", Icons.Default.Theaters, R.drawable.baseline_theaters_24),
    )

    //    "Business", "Personal", "Other"
    val accountOptions = listOf(
        AccountType(1, R.string.business, R.drawable.baseline_business_center_24),
        AccountType(2, R.string.personal, R.drawable.baseline_person_24),
    )
}