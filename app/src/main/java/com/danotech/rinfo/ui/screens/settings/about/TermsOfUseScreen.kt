package com.danotech.rinfo.ui.screens.settings.about

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsOfUseScreen(
    onBackClick: () -> Unit = {}
) {
    BackHandler {
        onBackClick()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terms of Use") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            val textSpace = 4.dp
            val paragraphSpace = 16.dp

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = innerPadding,
            ) {
                item {
                    // Developer Info
                    Text(
                        text = "This Privacy Policy describes how we collect, use, and share your personal information when you use our mobile application.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(paragraphSpace))
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(textSpace)
                    ) {
                        Text(
                            text = "1. Acceptance of Terms:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "By accessing or using the Service, you agree to these Terms of Use and all applicable laws and regulations.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(paragraphSpace))
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(textSpace)
                    ) {
                        Text(
                            text = "2. Use of the Service:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "You may use the Service for your personal or business purposes. You are responsible for any activity that occurs under your account.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(paragraphSpace))
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(textSpace)
                    ) {
                        Text(
                            text = "3. Privacy Policy:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Your use of the Service is also governed by our Privacy Policy, which can be found at [Privacy Policy Link]. By using the Service, you consent to the terms of our Privacy Policy.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(paragraphSpace))
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(textSpace)
                    ) {
                        Text(
                            text = "4. User Conduct:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "You agree not to use the Service for any unlawful or prohibited purposes, and you are solely responsible for your interactions with other users of the Service.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(paragraphSpace))
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(textSpace)
                    ) {
                        Text(
                            text = "5. Modification of Terms:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "We reserve the right to modify these Terms of Use at any time. Any changes will be effective upon posting on the Service. It is your responsibility to review these terms regularly.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(paragraphSpace))
                }

                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(textSpace)
                    ) {
                        Text(
                            text = "6. Contact Us:",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "If you have any questions or concerns about these Terms of Use, please contact us at [Contact Email Address].",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(paragraphSpace))
                }

                item {
                    // Developer Info
                    Text(
                        text = "Thank you for using our Service!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    )
}
