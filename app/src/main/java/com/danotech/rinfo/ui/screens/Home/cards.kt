package com.danotech.rinfo.ui.screens.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RatingDisplay
import com.example.compose.AppTheme

@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.cafe_javas),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(100.dp),
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

                RatingDisplay(
                    rating = 3,
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}


data class Review(
    val avatarResource: Int,
    val businessName: String,
    val comment: String
)

@Preview(showBackground = true)
@Composable
fun PreviewReviewCard() {
    val sampleReview = Review(
        avatarResource = R.drawable.baseline_person_24,
        businessName = "Cafe Javas",
        comment = "This place has the best coffee and sandwiches in town!"
    )

    AppTheme {
        ReviewCard(
            review = sampleReview,
            modifier = Modifier.padding(16.dp)
        )
    }
}
