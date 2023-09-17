package com.danotech.rinfo.ui.components

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.screens.business.components.IconAndText
import com.danotech.rinfo.ui.screens.home.HomesScreenViewModel
import com.danotech.rinfo.ui.screens.review.BusinessCardImage

/**
 * Review card
 * when clicked it redirects you to the review page
 * @param business
 * @param onReviewCardClicked takes an int which is the currents review's id
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessCard(
    business: Business,
    modifier: Modifier = Modifier,
    onReviewCardClicked: (Business) -> Unit = {},
    viewModel: HomesScreenViewModel = hiltViewModel(),
    @DimenRes paddingHorizontal: Int
) {
    val uiState = viewModel.uiState.collectAsState().value

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        onClick = { onReviewCardClicked(business) },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = paddingHorizontal)),
    ) {
        val imageSize = 100.dp

        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement
                .spacedBy(10.dp)
        ) {
            BusinessImageShimmer(
                size = imageSize,
                isLoading = uiState.imageLoading
            ) {
                BusinessCardImage(
                    imageSize = imageSize,
                    url = business.logo,
                    description = "${business.name} logo"
                )
            }


            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                IconAndText(
                    icon = Icons.Filled.LocationOn,
                    iconDes = stringResource(id = R.string.location),
                    text = business.address
                )

                IconAndText(
                    icon = Icons.Filled.Favorite,
                    iconDes = stringResource(id = R.string.favorites),
                    text = "${business.reviews} recommendations for this business."
                )
            }
        }
    }
}

