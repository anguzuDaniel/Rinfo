package com.danotech.rinfo.ui.screens.business.subsections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.SectionHeading
import com.danotech.rinfo.ui.components.SectionHeadingString
import com.danotech.rinfo.ui.components.TruncateText
import com.danotech.rinfo.ui.screens.business.components.ActionDetailsRow
import com.danotech.rinfo.ui.screens.business.components.IconAndText

@Composable
fun BusinessAboutSection(
    business: Business
) {
    var isShowingAllDescriptionText by remember {
        mutableStateOf(false)
    }

    val clickableText = if (isShowingAllDescriptionText) "less" else "Read more"

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.body_padding))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SectionHeadingString(
                text = business.name,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.body_padding))
            )

//                // if business is added to favorites then primary color is used for tint else white
//                if (!addToFavorite) {
//                    Icon(
//                        imageVector = Icons.Default.Favorite,
//                        contentDescription = stringResource(R.string.bookmark_business),
//                        tint = MaterialTheme.colorScheme.onBackground,
//                        modifier = Modifier.clickable {
//                            addToFavorite = !addToFavorite
//                        }
//                    )
//                } else {
//                    Icon(
//                        imageVector = Icons.Default.Favorite,
//                        contentDescription = stringResource(R.string.bookmark_business),
//                        tint = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.clickable {
//                            addToFavorite = !addToFavorite
//                        }
//                    )
//                }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconAndText(
                icon = Icons.Filled.Star,
                iconDes = "ratings",
                text = "${business.reviews}.0 / 5.0 (${business.reviews} reviews)"
            )

            IconAndText(
                icon = Icons.Filled.LocationOn,
                iconDes = stringResource(id = R.string.location),
                text = business.address
            )

            IconAndText(
                icon = Icons.Filled.Favorite,
                iconDes = stringResource(id = R.string.favorites),
                text = "${business.reviews} recommendations"
            )
        }

        ActionDetailsRow(
            businessName = business.name,
            email = business.email,
            phone = business.phone,
            whatsapp = business.phone,
            onDirectionClicked = {
//                    onDirectionClicked(business.address)
            },
        )

        SectionHeading(
            text = R.string.description,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.body_padding))
        )

        if (!isShowingAllDescriptionText) {
            TruncateText(
                text = business.description,
                maxWords = 20,  // Set the desired maximum number of words
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Text(
                text = business.description,
                style = TextStyle(
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        ClickableText(
            text = AnnotatedString(clickableText),
            style = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.primary),
            onClick = { isShowingAllDescriptionText = !isShowingAllDescriptionText }
        )
    }
}