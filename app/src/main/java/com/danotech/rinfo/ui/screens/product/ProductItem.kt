package com.danotech.rinfo.ui.screens.product

import android.view.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.screens.business.components.BusinessImageShimmer
import com.danotech.rinfo.ui.screens.business.components.Product
import com.danotech.rinfo.ui.screens.business.components.SubSectionHeading
import com.danotech.rinfo.ui.screens.business.subsections.ProductRatingRow
import com.danotech.rinfo.ui.screens.review.FirebaseImageDisplay
import kotlinx.coroutines.launch

@Composable
fun ProductItem(
    product: Product,
    window: Window
) {
    val coroutineScope = rememberCoroutineScope()
    val view = LocalView.current
    val windowInsetsController =
        WindowCompat.getInsetsController(window, view)


    // sets the color of the status bar
    // according to the image shown currently
    SideEffect {
        coroutineScope.launch {
            window.statusBarColor = Color.Transparent.toArgb()
        }
    }


    LazyColumn(
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
        )
    ) {
        item {
            val size = 300.dp

            Box(
                modifier = Modifier
                    .height(size)
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.BottomEnd
            ) {
                BusinessImageShimmer(
                    isLoading = false
                ) {
                    FirebaseImageDisplay(
                        isFullScreen = true,
                        url = product.image,
                        description = product.description,
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)),
                    )
                }
            }
        }

        item {
            ProductDescription(
                product = product,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        MaterialTheme
                            .colorScheme.background
                    )
            )
        }
    }
}


@Composable
private fun ProductDescription(
    product: Product,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ProductRatingRow(
                price = product.name,
                rating = 4
            )

            Text(text = product.description)

            SubSectionHeading(text = R.string.similar_products)
        }
    }
}