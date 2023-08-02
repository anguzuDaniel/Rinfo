package com.danotech.rinfo.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.SubHeadingText
import com.danotech.rinfo.ui.screens.appbars.RinfoTopAppBar
import com.example.compose.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RinfoTopAppBar(
                title = "account",
                isShowingHomePage = false,
                onBackButtonClicked = {
                    // Back button clicked
                },
            )
        },
    ) { innerPadding ->
        EditAccountContent(
            innerPadding = innerPadding
        )
    }
}

@Composable
fun EditAccountContent(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.body_padding)),
        contentPadding = innerPadding,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = stringResource(id = R.string.profile_image)
            )
        }

        item {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubHeadingText(
                    text = R.string.personal_account,
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.body_padding)))

                SettingsClickableComp(
                    name = R.string.dark_mode,
                    icon = Icons.Rounded.FavoriteBorder,
                    iconDesc = R.string.dark_mode,
                ) {
                    // here you can do anything - navigate - open other settings, ...
                }

                SettingsClickableComp(
                    name = R.string.notifications,
                    icon = Icons.Rounded.FavoriteBorder,
                    iconDesc = R.string.notifications,
                ) {
                    // here you can do anything - navigate - open other settings, ...
                }

                SettingsClickableComp(
                    name = R.string.account,
                    icon = Icons.Rounded.FavoriteBorder,
                    iconDesc = R.string.account,
                ) {
                    // here you can do anything - navigate - open other settings, ...
                }

                SettingsClickableComp(
                    name = R.string.logout,
                    icon = Icons.Rounded.FavoriteBorder,
                    iconDesc = R.string.logout,
                ) {
                    // here you can do anything - navigate - open other settings, ...
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsClickableComp(
    icon: ImageVector,
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        onClick = onClick,
    ) {
        Card(
            elevation = CardDefaults.cardElevation()
        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = 2.dp,
                    horizontal = dimensionResource(id = R.dimen.setting_card_padding)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = name),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    Icon(
                        Icons.Rounded.KeyboardArrowRight,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(id = R.string.arrow_forward)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EditAccountPreview() {
    AppTheme {
        SettingPage()
    }
}

@Preview(showBackground = true)
@Composable
fun EditAccountDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        SettingPage()
    }
}