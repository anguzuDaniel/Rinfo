package com.danotech.rinfo.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlin.math.min

fun <T> LazyListScope.gridItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount
//    val numberOfItemsByRow = LocalConfiguration.current.screenWidthDp / 200 // you can replace 200 by the minimum size you want your cells to have.


    items(rows, key = { it.hashCode() }) { rowIndex ->
        Row(
            horizontalArrangement = horizontalArrangement, modifier = modifier
        ) {
            for (columnIndex in 0 until columnCount) {
                val itemIndex = rowIndex * columnCount + columnIndex
                if (itemIndex < size) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true), propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(Modifier.weight(1F, fill = true))
                }
            }
        }
    }
}

fun <T> LazyListScope.staggeredItems(
    data: List<T>,
    columnCount: Int,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    val size = data.count()
    val rows = if (size == 0) 0 else 1 + (size - 1) / columnCount

//    items(rows, key = { it.hashCode() }) { rowIndex ->
//        StaggeredRow(
//            columnCount = columnCount,
//            rowIndex = rowIndex,
//            data = data,
//            itemContent = itemContent
//        )

    item {
        // on below line we are calling our
        // custom staggered vertical grid item.
        CustomStaggeredVerticalGrid(
            // on below line we are specifying
            // number of columns for our grid view.
            numColumns = 2,

            // on below line we are adding padding
            // from all sides for our grid view.
            modifier = Modifier.padding(5.dp)
        ) {
            // inside staggered grid view we are
            // adding images for each item of grid.
            data.forEach { d ->
                itemContent(d)
            }
        }
    }

//    }
}


// on below line we are creating a custom
// composable item for our grid view item.
@Composable
fun CustomStaggeredVerticalGrid(
    // on below line we are specifying
    // parameters as modifier, num of columns
    modifier: Modifier = Modifier,
    numColumns: Int = 2,
    content: @Composable () -> Unit
) {
    // inside this grid we are creating
    // a layout on below line.
    Layout(
        // on below line we are specifying
        // content for our layout.
        content = content,
        // on below line we are adding modifier.
        modifier = modifier
    ) { measurable, constraints ->
        // on below line we are creating a variable for our column width.
        val columnWidth = (constraints.maxWidth / numColumns)

        // on the below line we are creating and initializing our items constraint widget.
        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        // on below line we are creating and initializing our column height
        val columnHeights = IntArray(numColumns) { 0 }

        // on below line we are creating and initializing placeables
        val placeables = measurable.map { measurable ->
            // inside placeable we are creating
            // variables as column and placeables.
            val column = testColumn(columnHeights)
            val placeable = measurable.measure(itemConstraints)

            // on below line we are increasing our column height/
            columnHeights[column] += placeable.height
            placeable
        }

        // on below line we are creating a variable for
        // our height and specifying height for it.
        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        // on below line we are specifying height and width for our layout.
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            // on below line we are creating a variable for column y pointer.
            val columnYPointers = IntArray(numColumns) { 0 }

            // on below line we are setting x and y for each placeable item
            placeables.forEach { placeable ->
                // on below line we are calling test
                // column method to get our column index
                val column = testColumn(columnYPointers)

                placeable.place(
                    x = columnWidth * column,
                    y = columnYPointers[column]
                )

                // on below line we are setting
                // column y pointer and incrementing it.
                columnYPointers[column] += placeable.height
            }
        }
    }
}

// on below line we are creating a test column method for setting height.
private fun testColumn(columnHeights: IntArray): Int {
    // on below line we are creating a variable for min height.
    var minHeight = Int.MAX_VALUE

    // on below line we are creating a variable for column index.
    var columnIndex = 0

    // on below line we are setting column  height for each index.
    columnHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            columnIndex = index
        }
    }
    // at last we are returning our column index.
    return columnIndex
}

@Composable
private fun <T> StaggeredRow(
    columnCount: Int,
    rowIndex: Int,
    data: List<T>,
    itemContent: @Composable (T) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val start = rowIndex * columnCount
        val end = min(start + columnCount, data.size)
        for (index in start until end) {
            itemContent(data[index])
        }
    }
}



