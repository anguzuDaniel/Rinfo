package com.danotech.rinfo.ui.screens.review

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.ui.RinfoAppUiState
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.components.Review
import com.danotech.rinfo.ui.screens.RInfoScreen
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.example.compose.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    rinfoAppUiState: RinfoAppUiState,
    onBackPressed: () -> Unit = {},
) {
    BackHandler {
        onBackPressed()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = false,
                showBackgroundColor = false,
                onBackButtonClicked = onBackPressed,
            )
        },
    ) { innerPadding ->
        LazyColumn() {
            item {
                ReviewContent(
                    review = rinfoAppUiState.currentReview
                )
            }
        }
    }
}

@Composable
fun ReviewContent(
    modifier: Modifier = Modifier,
    review: Review = LocalReviewProvider.defaultReview
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = review.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.body_padding))) {

            Text(
                text = "Cafe Javas",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    RatingStars(rating = 3)

                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_medium)))

                    Text(
                        text = "4.9",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.favorites),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "We're committed to great food, great coffee, great service, an experience that will make your time with us fabulous. All visuals are serving suggestions only.\n" +
                        "Prices are quoted in Uganda Shillings and inclusive of VAT.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reviews",
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))

                Text(
                    text = stringResource(R.string.view_all),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_medium)))

            ReviewCard(
                review = CustomerReview(
                    name = "Anguzu Daniel",
                    rating = 3,
                    review = "This is a very good restaurant."
                )
            )

            ReviewCard(
                review = CustomerReview(
                    name = "Mugabi Alex",
                    rating = 5,
                    review = "The waiters were not friendly."
                )
            )
        }
    }
}

@Composable
fun ReviewCard(
    review: CustomerReview,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(id = R.dimen.card_padding))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )

                Text(
                    text = review.name,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.text_spacer)))

            RatingStars(
                rating = review.rating
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.review,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    AppTheme {
        ReviewScreen(
            rinfoAppUiState = RinfoAppUiState(
                currentReview = LocalReviewProvider.defaultReview
            ),
        )
    }
}

@Preview
@Composable
fun ReviewScreenPreviewDark() {
    AppTheme(
        darkTheme = true
    ) {
        ReviewScreen(
            rinfoAppUiState = RinfoAppUiState(
                currentReview = LocalReviewProvider.defaultReview
            ),
        )
    }
}

data class CustomerReview(
    val name: String,
    val rating: Int,
    val review: String
)