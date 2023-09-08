package com.danotech.rinfo.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.data.LocalReviewProvider
import com.danotech.rinfo.model.local.Category

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectBusinessCategory(
    modifier: Modifier = Modifier,
    onAccountTypeSelected: (Category) -> Unit = {}
) {
    val listItems = LocalReviewProvider.categories

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    val focusRequester = remember {
        FocusRequester()
    }

    Box(
        modifier = modifier
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        ) {
            TextField(
                value = selectedItem.name,
                onValueChange = { onAccountTypeSelected(selectedItem) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                    )
                },
                modifier = Modifier
                    .menuAnchor(),
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            // menu
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .menuAnchor()
            ) {
                // this is a column scope
                // all the items are added vertically
                listItems.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(
                        text = { Text(text = selectedOption.name) },
                        onClick = {
                            selectedItem = selectedOption
                            onAccountTypeSelected(selectedOption)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun ReviewDropdownActionOptions(
    onReportClicked: () -> Unit = {},
    onReplyClicked: () -> Unit = {},
    onLikeClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
) {
    LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Share") },
                onClick = onShareClicked
            )
            DropdownMenuItem(
                text = { Text("Like") },
                onClick = onLikeClicked
            )
            DropdownMenuItem(
                text = { Text("Reply") },
                onClick = onReplyClicked
            )
            DropdownMenuItem(
                text = { Text("Report") },
                onClick = onReportClicked
            )
        }
    }
}

@Composable
fun CurrentUserReviewDropdownActionOptions(
    onEditClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {}
) {
    LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = onEditClicked
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    onDeleteClicked()
                    expanded = false
                }
            )
        }
    }
}