package com.danotech.rinfo.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danotech.rinfo.R
import com.example.compose.AppTheme

/**
 * reusable button
 * provide a string resource for the button name
 * provide a click handler
 */
@Composable
fun RinfoButton(
    @StringRes name: Int,
    onClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onClicked },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = stringResource(id = name))
    }
}

/**
 * FAB for the search button
 * provide a click handler
 * sends your the search screen
 */
@Composable
fun RinfoFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.search),
        )
    }
}

/**
 * reusable text input
 */
@Composable
fun CategoryIconButton(
    description: String,
    @DrawableRes icon: Int,
    @StringRes name: Int,
    onCategoryClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            IconButton(
                onClick = { onCategoryClick() },
                modifier = modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = description
                )
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/**
 * reusable text input
 * show options for the search screen
 */
@Composable
fun ShowOptionButton(
    @StringRes name: Int,
    onClick: () -> Unit,
    active: Boolean = false,
    modifier: Modifier = Modifier
) {
    val containerColor =
        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

    val contentColor =
        if (active) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary


    Button(
        onClick = { onClick },
        shape = MaterialTheme.shapes.small,
        colors = buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        enabled = active,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = name),
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor,
            fontSize = 10.sp,
        )
    }
}

@Preview
@Composable
fun CategoryIconButtonPreview() {
    CategoryIconButton(
        description = "test",
        icon = R.drawable.baseline_dining_24,
        name = R.string.all
    )
}

@Preview(showBackground = true)
@Composable
fun ShowOptionButtonPreview() {
    AppTheme {
        ShowOptionButton(
            name = R.string.all,
            active = false,
            onClick = {},
        )
    }
}