package com.danotech.rinfo.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.screens.business.IconAndText
import com.danotech.rinfo.ui.screens.home.HomesScreenViewModel

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
    viewModel: HomesScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val defaultProfilePicture: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.no_image
    )

    val bitmap = remember {
        mutableStateOf(defaultProfilePicture)
    }

    LaunchedEffect(viewModel) {
        viewModel.getImage(
            businessId = business.id,
            bitmap = bitmap
        )
    }

    val uiState = viewModel.uiState.collectAsState().value

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        onClick = { onReviewCardClicked(business) },
        modifier = modifier
            .fillMaxWidth(),
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
                Column(
                    modifier = Modifier
                        .size(imageSize),
                ) {
                    Image(
                        bitmap = bitmap.value.asImageBitmap(),
                        contentDescription = "${business.name} logo",
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop,
                    )
                }
            }


            Column {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                IconAndText(
                    icon = Icons.Filled.LocationOn,
                    iconDes = stringResource(id = R.string.location),
                    text = business.address
                )

                Spacer(modifier = Modifier.height(4.dp))

                IconAndText(
                    icon = Icons.Filled.Favorite,
                    iconDes = stringResource(id = R.string.favorites),
                    text = "${business.reviews} recommendations for this business."
                )
            }
        }
    }
}

