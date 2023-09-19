package com.danotech.rinfo.ui.screens.settings.about

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.centeredTopAppBar

@Composable
fun PrivacyPolicyScreen(
    onBackClick: () -> Unit = {}
) {
    BackHandler {
        onBackClick()
    }

    Scaffold(topBar = {
        centeredTopAppBar(
            onBackClick = onBackClick,
            text = R.string.privacy_policy,
            hasBack = true
        )
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
