package com.danotech.rinfo.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.components.SettingSwitch

/**
 * Clickable setting subsection redirector and action
 * used to redirect to particular page
 * or used for particular page
 * @param leadingIcon leading icon shown before the text/name
 * @param icon
 * @param iconDesc the icon description
 * @param name text shown for the setting
 * @param settingType
 * @param onClick call back function, called when clicked
 * @param opensDialogWhenClicked if true the end icon is not shown
 */
@Composable
fun SettingsClickableComp(
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector,
    icon: ImageVector,
    description: String = "",
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    settingType: SettingType = SettingType.SWITCH,
    onClick: () -> Unit,
    opensDialogWhenClicked: Boolean = false
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                modifier = Modifier.weight(1f),
                tint = if (!opensDialogWhenClicked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
            )

            Column(
                modifier = Modifier.weight(
                    if (!opensDialogWhenClicked) 3f else 4f
                ),
                verticalArrangement = if (description != "") Arrangement.spacedBy(5.dp) else Arrangement.spacedBy(
                    0.dp
                ),
            ) {
                Text(
                    text = stringResource(id = name),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (!opensDialogWhenClicked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                    ),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                )

                if (description != "") {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1.0f))
            if (settingType == SettingType.SWITCH) {
                SettingSwitch(
                    clicked = false,
                    onSwitchChanged = {},
                    modifier = Modifier.weight(1f)
                )
            } else {
                if (!opensDialogWhenClicked) {
                    Icon(
                        Icons.Rounded.KeyboardArrowRight,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(id = R.string.arrow_forward),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
    }
}