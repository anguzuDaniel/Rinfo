package com.danotech.rinfo.ui.components.rinfo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.components.Rinfo

public val Rinfo.Logoout: ImageVector
    get() {
        if (_logoout != null) {
            return _logoout!!
        }
        _logoout = Builder(name = "Logoout", defaultWidth = 1080.0.dp, defaultHeight = 1080.0.dp,
                viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(357.301f, 342.188f)
                    curveTo(359.387f, 341.043f, 360.66f, 338.828f, 360.66f, 336.41f)
                    lineTo(360.66f, 299.66f)
                    curveTo(360.66f, 297.445f, 359.586f, 295.363f, 357.707f, 294.086f)
                    curveTo(355.824f, 292.809f, 353.473f, 292.609f, 351.391f, 293.414f)
                    curveTo(297.914f, 315.246f, 263.316f, 366.707f, 263.316f, 424.551f)
                    curveTo(263.316f, 502.68f, 326.871f, 566.23f, 405.0f, 566.23f)
                    curveTo(483.129f, 566.23f, 546.684f, 502.68f, 546.684f, 424.551f)
                    curveTo(546.684f, 366.773f, 512.086f, 315.316f, 458.543f, 293.414f)
                    curveTo(456.461f, 292.609f, 454.109f, 292.809f, 452.227f, 294.086f)
                    curveTo(450.414f, 295.363f, 449.273f, 297.445f, 449.273f, 299.66f)
                    lineTo(449.273f, 336.344f)
                    curveTo(449.273f, 338.762f, 450.547f, 340.977f, 452.633f, 342.188f)
                    curveTo(481.988f, 359.184f, 500.195f, 390.758f, 500.195f, 424.551f)
                    curveTo(500.195f, 477.086f, 457.469f, 519.813f, 405.0f, 519.813f)
                    curveTo(352.531f, 519.813f, 309.805f, 477.086f, 309.805f, 424.551f)
                    curveTo(309.805f, 390.758f, 328.012f, 359.25f, 357.301f, 342.188f)
                    close()
                    moveTo(357.301f, 342.188f)
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(435.836f, 378.801f)
                    lineTo(435.836f, 274.672f)
                    curveTo(435.836f, 266.477f, 432.68f, 258.68f, 426.832f, 252.77f)
                    curveTo(420.988f, 246.992f, 413.262f, 243.77f, 405.0f, 243.77f)
                    curveTo(388.004f, 243.77f, 374.098f, 257.605f, 374.098f, 274.672f)
                    lineTo(374.098f, 378.801f)
                    curveTo(374.098f, 395.863f, 388.004f, 409.703f, 405.0f, 409.703f)
                    curveTo(421.996f, 409.703f, 435.836f, 395.863f, 435.836f, 378.801f)
                    close()
                    moveTo(435.836f, 378.801f)
                }
            }
        }
        .build()
        return _logoout!!
    }

private var _logoout: ImageVector? = null
