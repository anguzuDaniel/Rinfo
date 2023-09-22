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
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.components.Rinfo

public val Rinfo.Search: ImageVector
    get() {
        if (_search != null) {
            return _search!!
        }
        _search = Builder(name = "Search", defaultWidth = 1080.0.dp, defaultHeight = 1080.0.dp,
                viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            path(fill = SolidColor(Color(0xFF717273)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(356.484f, 280.297f)
                curveTo(398.535f, 280.297f, 432.625f, 314.383f, 432.625f, 356.414f)
                curveTo(432.625f, 398.48f, 398.535f, 432.594f, 356.484f, 432.594f)
                curveTo(314.43f, 432.594f, 280.344f, 398.488f, 280.344f, 356.414f)
                curveTo(280.344f, 314.383f, 314.43f, 280.297f, 356.484f, 280.297f)
                moveTo(356.484f, 243.0f)
                curveTo(293.941f, 243.0f, 243.047f, 293.895f, 243.047f, 356.418f)
                curveTo(243.047f, 418.984f, 293.941f, 469.852f, 356.48f, 469.852f)
                curveTo(419.031f, 469.852f, 469.906f, 418.98f, 469.906f, 356.418f)
                curveTo(469.906f, 293.895f, 419.031f, 243.0f, 356.484f, 243.0f)
                close()
                moveTo(356.484f, 243.0f)
            }
            path(fill = SolidColor(Color(0xFF717273)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(532.84f, 566.969f)
                curveTo(524.871f, 566.969f, 516.949f, 563.945f, 510.871f, 557.867f)
                lineTo(455.707f, 502.691f)
                curveTo(443.563f, 490.563f, 443.563f, 470.918f, 455.707f, 458.746f)
                curveTo(467.855f, 446.613f, 487.508f, 446.613f, 499.652f, 458.746f)
                lineTo(554.816f, 513.93f)
                curveTo(566.965f, 526.066f, 566.965f, 545.727f, 554.816f, 557.867f)
                curveTo(548.746f, 563.945f, 540.816f, 566.969f, 532.84f, 566.969f)
                close()
                moveTo(532.84f, 566.969f)
            }
            path(fill = SolidColor(Color(0xFF717273)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(444.23f, 420.938f)
                lineTo(474.539f, 451.234f)
                lineTo(448.172f, 477.617f)
                lineTo(417.859f, 447.324f)
                close()
                moveTo(444.23f, 420.938f)
            }
        }
        .build()
        return _search!!
    }

private var _search: ImageVector? = null
