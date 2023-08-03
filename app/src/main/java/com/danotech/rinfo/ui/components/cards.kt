package com.danotech.rinfo.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.data.LocalReviewProvider
import com.example.compose.AppTheme

/**
 * Review card
 * when clicked it redirects you to the review page
 * @param review
 * @param onReviewCardClicked takes an int which is the currents review's id
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier,
    onReviewCardClicked: (Review) -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        onClick = { onReviewCardClicked(review) },
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = review.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
            )

            Column() {
                Text(
                    text = review.businessName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = review.comment,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                RatingStars(
                    rating = review.rating,
                )
            }
        }
    }
}


data class Review(
    val id: Int = 0,
    // to be changed to receive url links
    @DrawableRes val imageUrl: Int,
    val avatarResource: Int,
    val businessName: String,
    val rating: Int = 0,
    val comment: String
)

@Preview(showBackground = true)
@Composable
fun PreviewReviewCard() {
    AppTheme {
        ReviewCard(
            review = LocalReviewProvider.defaultReview,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.body_padding))
        )
    }
}
