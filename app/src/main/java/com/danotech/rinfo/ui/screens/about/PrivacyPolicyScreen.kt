package com.danotech.rinfo.ui.screens.about

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
fun PrivacyPolicyScreen(
    onBackClick: () -> Unit = {}
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text("Privacy Policy") }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        })
    }, content = { innerPadding ->
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
                        text = "Information We Collect:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "- Personal Information: We may collect your name, email address, and other contact information when you create an account.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "- Usage Information: We may collect information about how you use the app, such as your interactions and preferences.",
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
                        text = "How We Use Your Information:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "- We use your personal information to provide and improve our services.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "- We may share your information with third-party service providers.",
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
                        text = "Your Choices:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "- You can update or delete your account information at any time.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "- You can opt-out of certain data collection.",
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
                        text = "Security:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "- We take reasonable measures to protect your information.",
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
                        text = "Changes to this Privacy Policy:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "- We may update this policy from time to time.",
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
                        text = "Contact Us:",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "- If you have questions about this Privacy Policy, contact us at anguzudaniel@gmail.com.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    })
}
