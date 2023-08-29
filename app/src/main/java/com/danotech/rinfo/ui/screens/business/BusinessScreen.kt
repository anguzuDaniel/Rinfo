package com.danotech.rinfo.ui.screens.business

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.model.Business
import com.danotech.rinfo.ui.components.RatingStars
import com.danotech.rinfo.ui.components.RinfoButton
import com.danotech.rinfo.ui.components.RinfoFAB
import com.danotech.rinfo.ui.components.TruncateText
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.danotech.rinfo.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessScreen(
    businessId: String,
    reviewerUserId: String,
    viewModel: BusinessViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onSearchIconClicked: () -> Unit = {},
    onFabBtnClicked: () -> Unit = {},
    onShowReviewPageClicked: () -> Unit = {},
    onDirectionClicked: (String) -> Unit = {},
) {
    val context = LocalContext.current

    BackHandler {
        onBackPressed()
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getBusinessById(businessId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = if (uiState.isLoading) {
            {}
        } else {
            {
                RinfoTopAppBar(
                    isShowingHomePage = false,
                    showBackgroundColor = false,
                    onBackButtonClicked = onBackPressed,
                    actions = {
                        IconButton(
                            onClick = onSearchIconClicked,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    shape = CircleShape
                                ),
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = stringResource(R.string.bookmark_business),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = if (uiState.isLoading) {
            {}
        } else {
            { RinfoFAB(onClick = onFabBtnClicked) }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        if (!uiState.isLoading) {
            BusinessContent(
                business = uiState.currentBusiness,
                onShowReviewPageClicked = onShowReviewPageClicked,
                onDirectionClicked = {
                    onDirectionClicked(it)
                },
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .animateContentSize(
                        animationSpec = (tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        ))
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun collectBusinessState(businessFlow: Flow<Business?>): State<Business?> {
    // Collect the flow and convert it into a Compose State
    return businessFlow.collectAsState(initial = null)
}

@Composable
fun BusinessContent(
    modifier: Modifier = Modifier,
    business: Business,
    onShowReviewPageClicked: () -> Unit = {},
    onDirectionClicked: (String) -> Unit = {},
) {
    var isShowingAllDescriptionText by remember {
        mutableStateOf(false)
    }

    val clickableText = if (isShowingAllDescriptionText) "less" else "See more"

    Column {
        Box(
            modifier = modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(Color.White),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.cafe_javas),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        dimensionResource(id = R.dimen.body_padding)
                    ),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.ExtraBold
                )

                RatingRow(reviews = business.reviews)
            }
        }


        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.body_padding))
        ) {
            item {
                IconAndText(
                    icon = Icons.Filled.LocationOn,
                    iconDes = stringResource(id = R.string.location),
                    text = business.address
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                IconAndText(
                    icon = Icons.Filled.Favorite,
                    iconDes = stringResource(id = R.string.favorites),
                    text = "${business.reviews} recommendations"
                )
                Spacer(modifier = Modifier.height(10.dp))
            }


            item {
                ActionDetailsRow(
                    businessName = business.name,
                    email = business.email,
                    phone = business.phone,
                    whatsapp = business.phone,
                    onDirectionClicked = {
                        onDirectionClicked(business.address)
                    },
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Text(
                    text = stringResource(id = R.string.about),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
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
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                ClickableText(
                    text = AnnotatedString(clickableText),
                    style = MaterialTheme.typography.bodyMedium
                        .copy(color = if (isShowingAllDescriptionText) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary),
                    onClick = { isShowingAllDescriptionText = !isShowingAllDescriptionText }
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {

            }

            item {
                OutlinedButton(
                    onClick = onShowReviewPageClicked,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        1.dp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.see_reviews),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun ActionDetailsRow(
    businessName: String,
    email: String = "",
    whatsapp: String = "",
    phone: String = "",
    onDirectionClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val sendEmailLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        // Handle the result if needed
    }

    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")  // This ensures only email apps are selected
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email)) // Email address
        putExtra(Intent.EXTRA_SUBJECT, "Subject") // Email subject
        putExtra(Intent.EXTRA_TEXT, "Hello,") // Email body
    }

    val message = "Hello ${businessName}!" // Message content

    val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://api.whatsapp.com/send?phone=$whatsapp&text=$message")
    }

    val dialerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ ->
        // Handle the result if needed
    }

    val callIntent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CallToActionButton(
            icon = Icons.Filled.Phone,
            name = R.string.call,
            onClicked = {
                dialerLauncher.launch(callIntent)
            },
            modifier = Modifier.weight(1f)
        )

        CallToActionButton(
            icon = Icons.Filled.Whatsapp,
            name = R.string.whatsapp,
            onClicked = {
                context.startActivity(whatsappIntent)
            },
            modifier = Modifier.weight(1f)
        )

        CallToActionButton(
            icon = Icons.Filled.Email,
            name = R.string.email,
            onClicked = {
                sendEmailLauncher.launch(emailIntent)
            },
            modifier = Modifier.weight(1f)
        )

        CallToActionButton(
            icon = Icons.Filled.Directions,
            name = R.string.directions,
            onClicked = onDirectionClicked,
            modifier = Modifier.weight(1f)
        )

        CallToActionButton(
            icon = Icons.Filled.Share,
            name = R.string.share,
            onClicked = { /*TODO*/ },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CallToActionButton(
    icon: ImageVector,
    name: Int,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.medium)
    ) {
        IconButton(
            onClick = onClicked,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceVariant),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = stringResource(id = name)
            )
        }
    }
}

@Composable
fun RecommendButton(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    RinfoButton(
        name = R.string.recommend,
        onClicked = onClicked,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
    )
}


@Composable
fun IconAndText(
    icon: ImageVector,
    iconDes: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDes,
            modifier = Modifier.size(15.dp),
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.ExtraLight
        )
    }
}

@Composable
fun RatingRow(
    reviews: Int = 0
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingStars(rating = reviews)

        Text(
            text = "${reviews}.0",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.ExtraBold
        )
    }
    Spacer(modifier = Modifier.padding(5.dp))
}



@Preview(showBackground = true)
@Composable
fun CallToActionPreview() {
    AppTheme {
        Surface {
            CallToActionButton(
                icon = Icons.Filled.Phone,
                name = R.string.call,
                onClicked = { /*TODO*/ },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecommendPreview() {
    AppTheme {
        Surface {
            RecommendButton(onClicked = { /*TODO*/ })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionRowPreview() {
    AppTheme {
        Surface {
            ActionDetailsRow(
                businessName = "Business Name",
                email = "",
                whatsapp = "",
                phone = ""
            )
        }
    }
}