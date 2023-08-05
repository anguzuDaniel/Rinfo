package com.danotech.rinfo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.danotech.rinfo.ui.theme.AppTheme

@Composable
fun SettingSwitch(
    switchOn: Boolean,
    onSwitchChanged: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var switchOn by remember { mutableStateOf(switchOn) }

    Box(
        modifier = modifier
    ) {
        Switch(
            checked = switchOn,
            onCheckedChange = { switchOn_ ->
                switchOn = switchOn_
                onSwitchChanged(switchOn_)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onSecondaryContainer,
                checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingSwitchPreview() {
    AppTheme() {
        SettingSwitch(
            switchOn = true,
            onSwitchChanged = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingSwitchDarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        SettingSwitch(
            switchOn = true,
            onSwitchChanged = {},
        )
    }
}