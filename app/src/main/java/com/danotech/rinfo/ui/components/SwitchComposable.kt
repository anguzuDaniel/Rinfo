package com.danotech.rinfo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.theme.AppTheme

@Composable
fun SettingSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onSwitchChanged: (Boolean) -> Unit = {},
) {
    var clicked by remember { mutableStateOf(checked) }
    // this is to disable the ripple effect
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = modifier
    ) {
        Switch(
            checked = clicked,
            onCheckedChange = {
                clicked = true
                onSwitchChanged(it)
            },
            interactionSource = interactionSource,
            thumbContent = if (clicked) {
                {
                    AnimatedVisibility(visible = clicked) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier
                                .size(SwitchDefaults.IconSize),
                        )
                    }
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
            checked = false,
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
            checked = true,
            onSwitchChanged = {},
        )
    }
}