package com.danotech.rinfo.ui.components.rinfo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.components.Rinfo

public val Rinfo.Darksolid: ImageVector
    get() {
        if (_darksolid != null) {
            return _darksolid!!
        }
        _darksolid = Builder(name = "Darksolid", defaultWidth = 1080.0.dp, defaultHeight =
                1080.0.dp, viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(419.082f, 243.867f)
                    curveTo(329.996f, 243.867f, 257.777f, 316.008f, 257.777f, 405.0f)
                    curveTo(257.777f, 493.992f, 329.996f, 566.133f, 419.082f, 566.133f)
                    curveTo(471.867f, 566.133f, 518.73f, 540.793f, 548.133f, 501.688f)
                    curveTo(551.469f, 497.25f, 552.008f, 491.309f, 549.527f, 486.344f)
                    curveTo(547.043f, 481.379f, 541.965f, 478.242f, 536.406f, 478.242f)
                    curveTo(475.664f, 478.242f, 426.414f, 429.055f, 426.414f, 368.379f)
                    curveTo(426.414f, 330.949f, 445.141f, 297.887f, 473.809f, 278.027f)
                    curveTo(478.371f, 274.867f, 480.754f, 269.402f, 479.965f, 263.91f)
                    curveTo(479.18f, 258.418f, 475.352f, 253.844f, 470.086f, 252.09f)
                    curveTo(454.035f, 246.754f, 436.883f, 243.867f, 419.082f, 243.867f)
                    close()
                    moveTo(419.082f, 243.867f)
                }
            }
        }
        .build()
        return _darksolid!!
    }

private var _darksolid: ImageVector? = null
