package com.danotech.rinfo.ui.screens.business.bottomSheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import java.util.Locale

@Composable
fun ButtonRow(
    onCancel: () -> Unit,
    onSubmit: () -> Unit,
    submitButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            modifier = modifier.weight(1f),
            onClick = onCancel,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(stringResource(R.string.cancel).uppercase(Locale.getDefault()))
        }
        Button(
            modifier = modifier.weight(1f),
            onClick = onSubmit,
            enabled = submitButtonEnabled
        ) {
            Text(
                stringResource(R.string.ok).uppercase(Locale.getDefault()),
                color = Color.White
            )
        }
    }
}