package com.danotech.rinfo.ui.screens.product

import android.annotation.SuppressLint
import android.os.Build
import android.view.Window
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.screens.appbars.CenteredBottomBarLayout
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.screens.business.components.Product

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun BusinessProductScreen(
    productId: String,
    window: Window,
    onBackClick: () -> Unit = {},
) {
    val product = getProduct()

    BackHandler {
        onBackClick()
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                isShowingHomePage = false,
                showBackgroundColor = false,
                onBackButtonClicked = onBackClick,
                actions = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null
                    )
                }
            )
        },
        bottomBar = {
            CenteredBottomBarLayout(
                bottomBar = {
                    BottomAppBar {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            RinfoButton(
                                enabled = true,
                                name = R.string.buy_now,
                                onClick = {},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(4f)
                            )
                        }
                    }
                }) {}
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        ProductItem(
            product = product,
            window = window
        )
    }
}

@Composable
fun getProduct(): Product {
    return Product(
        productId = "1",
        BusinessId = "Business1",
        image = "https://images.unsplash.com/photo-1602143407151-7111542de6e8?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=2487&q=80",
        name = "stainless steel water bottle",
        description = "Description for Product 1",
        discount = "10%",
        price = "$20.99",
        rating = 4,
        reviews = 25,
        category = "Category A"
    )
}