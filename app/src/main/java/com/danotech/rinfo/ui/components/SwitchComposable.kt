package com.danotech.rinfo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
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
    modifier: Modifier = Modifier,
    clicked: Boolean,
    onSwitchChanged: (Boolean) -> Unit = {},
) {
    var switchOn by remember { mutableStateOf(clicked) }

    Box(
        modifier = modifier
    ) {
        Switch(
            checked = switchOn,
            onCheckedChange = {
                switchOn = it
            },
            thumbContent = if (switchOn) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(SwitchDefaults.IconSize)
                        ,
                    )
                }
            } else {
                null
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingSwitchPreview() {
    AppTheme {
        SettingSwitch(
            clicked = false,
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
            clicked = true,
            onSwitchChanged = {},
        )
    }
}