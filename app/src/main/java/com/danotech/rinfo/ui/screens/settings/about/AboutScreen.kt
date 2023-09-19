package com.danotech.rinfo.ui.screens.settings.about

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.centeredTopAppBar
import com.danotech.rinfo.ui.screens.RInfoScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutScreen(
    onBackClick: () -> Unit = {},
    onNavClick: (String) -> Unit = {},
) {
    BackHandler {
        onBackClick()
    }

    rememberLazyListState()

    Scaffold(
        topBar = {
            centeredTopAppBar(
                onBackClick = onBackClick,
                text = R.string.About,
                hasBack = true
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues = innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Links to various sections
                SectionLink(
                    title = stringResource(id = R.string.about_app),
                    onClick = {
                        onNavClick(RInfoScreen.AboutApp.name)
                    })
                SectionLink(
                    title = stringResource(id = R.string.privacy_policy),
                    onClick = {
                        onNavClick(RInfoScreen.PrivacyPolicy.name)
                    })
                SectionLink(
                    title = stringResource(id = R.string.terms_of_use),
                    onClick = {
                        onNavClick(RInfoScreen.TermsOfUse.name)
                    })
            }
        }
    )
}

@Composable
fun SectionLink(title: String, onClick: () -> Unit) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        color = MaterialTheme.colorScheme.onBackground, // Customize the link color
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
    )
}
