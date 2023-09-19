package com.danotech.rinfo.ui.screens.settings.feedback

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.FeedbackButton
import com.danotech.rinfo.ui.components.centeredTopAppBar

@Composable
fun FeedbackScreen(
    onBackClick: () -> Unit = {}
) {
    BackHandler {
        onBackClick()
    }

    var feedbackText by remember { mutableStateOf("") }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            centeredTopAppBar(
                onBackClick = onBackClick,
                text = R.string.feed_back,
                hasBack = true
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            // Feedback Input
            OutlinedTextField(
                value = feedbackText,
                onValueChange = { feedbackText = it },
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle feedback submission here
                        submitFeedback(feedbackText, context)
                    }
                ),
                placeholder = {
                    Text(text = stringResource(id = R.string.send_feed_back))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            FeedbackButton(
                modifier = Modifier
                    .fillMaxWidth(),
                isLoading = false,
                enabled = false,
                action = {
                    // Handle feedback submission here
                    submitFeedback(feedbackText, context)
                }
            )
        }
    }
}


private fun submitFeedback(feedback: String, context: Context) {
    if (feedback.isNotEmpty()) {
        // Send the feedback to your server or perform any other necessary action
        // You can also display a confirmation message to the user
        // For now, we'll just show a toast message
        Toast.makeText(context, "Feedback submitted: $feedback", Toast.LENGTH_SHORT).show()
    }
}