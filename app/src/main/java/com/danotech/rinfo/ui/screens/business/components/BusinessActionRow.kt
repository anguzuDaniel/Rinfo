package com.danotech.rinfo.ui.screens.business.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.danotech.rinfo.R

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
    ) {
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
    ) {
        // Handle the result if needed
    }

    val callIntent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phone")
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            CallToActionButton(
                icon = Icons.Filled.Phone,
                name = R.string.call,
                onClicked = {
                    dialerLauncher.launch(callIntent)
                },
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Whatsapp,
                name = R.string.whatsapp,
                onClicked = {
                    context.startActivity(whatsappIntent)
                },
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Email,
                name = R.string.email,
                onClicked = {
                    sendEmailLauncher.launch(emailIntent)
                },
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Directions,
                name = R.string.directions,
                onClicked = onDirectionClicked,
            )
        }

        item {
            CallToActionButton(
                icon = Icons.Filled.Share,
                name = R.string.share,
                onClicked = { /*TODO*/ },
            )
        }
    }
}