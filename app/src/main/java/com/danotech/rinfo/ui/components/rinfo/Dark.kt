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

public val Rinfo.Dark: ImageVector
    get() {
        if (_dark != null) {
            return _dark!!
        }
        _dark = Builder(name = "Dark", defaultWidth = 1080.0.dp, defaultHeight = 1080.0.dp,
                viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(419.09f, 273.164f)
                    curveTo(346.199f, 273.164f, 287.109f, 332.188f, 287.109f, 405.0f)
                    curveTo(287.109f, 477.813f, 346.199f, 536.836f, 419.09f, 536.836f)
                    curveTo(452.371f, 536.836f, 482.773f, 524.539f, 506.0f, 504.215f)
                    curveTo(443.684f, 490.359f, 397.094f, 434.805f, 397.094f, 368.379f)
                    curveTo(397.094f, 331.949f, 411.113f, 298.793f, 434.031f, 274.0f)
                    curveTo(429.129f, 273.445f, 424.145f, 273.164f, 419.09f, 273.164f)
                    close()
                    moveTo(257.781f, 405.0f)
                    curveTo(257.781f, 316.008f, 330.0f, 243.867f, 419.09f, 243.867f)
                    curveTo(436.887f, 243.867f, 454.043f, 246.754f, 470.09f, 252.09f)
                    curveTo(475.359f, 253.844f, 479.184f, 258.418f, 479.973f, 263.91f)
                    curveTo(480.762f, 269.402f, 478.375f, 274.867f, 473.813f, 278.027f)
                    curveTo(445.145f, 297.887f, 426.422f, 330.949f, 426.422f, 368.379f)
                    curveTo(426.422f, 429.055f, 475.66f, 478.242f, 536.402f, 478.242f)
                    curveTo(541.957f, 478.242f, 547.047f, 481.379f, 549.531f, 486.344f)
                    curveTo(552.012f, 491.309f, 551.477f, 497.25f, 548.137f, 501.688f)
                    curveTo(518.734f, 540.793f, 471.871f, 566.133f, 419.09f, 566.133f)
                    curveTo(330.0f, 566.133f, 257.781f, 493.992f, 257.781f, 405.0f)
                    close()
                    moveTo(257.781f, 405.0f)
                }
            }
        }
        .build()
        return _dark!!
    }

private var _dark: ImageVector? = null
