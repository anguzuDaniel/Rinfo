package com.danotech.rinfo.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danotech.rinfo.R
import com.danotech.rinfo.ui.ThemeViewModel
import com.danotech.rinfo.ui.components.SettingSwitch
import com.danotech.rinfo.ui.components.TruncateText
import com.danotech.rinfo.ui.screens.review.FirebaseImageDisplay

/**
 * Clickable setting subsection redirector and action
 * used to redirect to particular page
 * or used for particular page
 * @param leadingIcon leading icon shown before the text/name
 * @param iconDesc the icon description
 * @param name text shown for the setting
 * @param settingType
 * @param onClick call back function, called when clicked
 * @param opensDialogWhenClicked if true the end icon is not shown
 */
@Composable
fun SettingsClickableComp(
    modifier: Modifier = Modifier,
    hasImage: Boolean = false,
    leadingIcon: ImageVector,
    description: String = "",
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    settingType: SettingType = SettingType.SWITCH,
    onClick: () -> Unit = {},
    opensDialogWhenClicked: Boolean = false,
    isSwitchedOn: Boolean = false,
    themeViewModel: ThemeViewModel = hiltViewModel(),
    onSwitchClick: (Boolean) -> Unit = {}, // DO NOT REMOVE: should always be the last
) {
    Surface(
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!hasImage) {

                    Box(
                        modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.medium)
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = modifier
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            colors = IconButtonDefaults.iconButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            enabled = false
                        ) {
                            Icon(
                                imageVector = leadingIcon,
                                contentDescription = stringResource(id = iconDesc),
                                tint = if (!opensDialogWhenClicked) MaterialTheme.colorScheme.onSurface.copy(
                                    0.50f
                                ) else MaterialTheme.colorScheme.error.copy(
                                    0.50f
                                )
                            )
                        }
                    }
                } else {
                    FirebaseImageDisplay(
                        imageSize = 50.dp,
                        url = "https://firebasestorage.googleapis.com/v0/b/rinfo-5ee97.appspot.com/o/logos%2F1695129390062.jpg?alt=media&token=604b7c66-b0f5-42b9-9c26-ae30353dd4c8",
                        description = "User image",
                        shape = CircleShape
                    )
                }
            }

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
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = if (!opensDialogWhenClicked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                    ),
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.80f),
                    fontWeight = FontWeight.Bold
                )

                if (description != "") {
                    TruncateText(
                        text = description,
                        maxWords = 20,  // Set the desired maximum number of words
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.44f)
                    )
                }
            }

//            Spacer(modifier = Modifier.weight(1.0f))
            if (settingType == SettingType.SWITCH) {
                SettingSwitch(
                    checked = isSwitchedOn,
                    onSwitchChanged = onSwitchClick,
                    modifier = Modifier
                        .weight(1f)
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioHighBouncy,
                                stiffness = Spring.DampingRatioLowBouncy
                            )
                        )
                )
            } else {
                if (!opensDialogWhenClicked) {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        tint = if (themeViewModel.themeState.value.isDarkMode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                            0.50f
                        ),
                        contentDescription = stringResource(id = R.string.arrow_forward),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}