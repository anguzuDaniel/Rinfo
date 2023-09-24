package com.danotech.rinfo.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.screens.business.components.BusinessImageShimmer
import com.danotech.rinfo.ui.screens.business.components.IconAndText
import com.danotech.rinfo.ui.screens.review.FirebaseImageDisplay

@Composable
fun BusinessGridCard(
    business: Business,
    imageLoading: Boolean,
    onReviewCardClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier.clickable {
            onReviewCardClicked()
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Box {
                    BusinessImageShimmer(
                        isLoading = imageLoading
                    ) {
                        FirebaseImageDisplay(
//                            imageSize = 100.dp,
                            isFullScreen = true,
                            url = business.logo,
                            description = business.description,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement
                        .spacedBy(4.dp)
                ) {
                    Text(
                        text = business.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )

                    IconAndText(
                        icon = Icons.Filled.Category,
                        iconDes = stringResource(id = R.string.favorites),
                        text = business.businessCategory
                    )

                    IconAndText(
                        icon = Icons.Filled.LocationOn,
                        iconDes = stringResource(id = R.string.location),
                        text = business.address
                    )

                    IconAndText(
                        icon = Icons.Filled.Favorite,
                        iconDes = stringResource(id = R.string.favorites),
                        text = "${business.reviews} love it."
                    )
                }
            }
        }
    }
}