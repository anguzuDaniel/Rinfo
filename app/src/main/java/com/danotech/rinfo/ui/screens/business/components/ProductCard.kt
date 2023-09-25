package com.danotech.rinfo.ui.screens.business.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RinfoButtonWithIcon
import com.danotech.rinfo.ui.screens.business.subsections.ProductRatingRow
import com.danotech.rinfo.ui.screens.review.FirebaseImageDisplay

@Composable
fun ProductCard(
    product: Product,
    imageLoading: Boolean = false,
    onProductCardClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable(onClick = onProductCardClick),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Image
            BusinessImageShimmer(
                isLoading = false,
                modifier = Modifier.fillMaxWidth()
            ) {
                FirebaseImageDisplay(
                    isFullScreen = true,
                    url = product.image,
                    description = product.description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max) // Image takes its own height
                        .aspectRatio(1f)
                )
            }


            // Text content
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement
                    .spacedBy(4.dp)
            ) {
                ProductRatingRow(
                    price = product.price,
                    rating = product.rating,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                RinfoButtonWithIcon(
                    name = R.string.add_to_cart,
                    onClick = onProductCardClick,
                    icon = Icons.Filled.AddShoppingCart,
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

data class Product(
    val productId: String = "",
    val BusinessId: String = "",
    val image: String = "",
    val name: String = "",
    val description: String = "",
    val discount: String = "",
    val price: String = "",
    val rating: Int = 0,
    val reviews: Int = 0,
    val category: String = "",
)