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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business

/**
 * Review card
 * when clicked it redirects you to the review page
 * @param business
 * @param onReviewCardClicked takes an int which is the currents review's id
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewCard(
    business: Business,
    modifier: Modifier = Modifier,
    onReviewCardClicked: (Business) -> Unit = {}
) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        onClick = { onReviewCardClicked(business) },
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
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

                TruncateText(
                    text = business.description,
                    maxWords = 10,  // Set the desired maximum number of words
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                RatingStars(
                    rating = business.reviews,
                )
            }
        }
    }
}

