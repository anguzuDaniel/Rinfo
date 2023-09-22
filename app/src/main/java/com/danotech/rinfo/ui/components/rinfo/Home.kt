package com.danotech.rinfo.ui.components.rinfo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
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

public val Rinfo.Home: ImageVector
    get() {
        if (_home != null) {
            return _home!!
        }
        _home = Builder(name = "Home", defaultWidth = 1080.0.dp, defaultHeight = 1080.0.dp,
                viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(414.59f, 288.086f)
                curveTo(408.613f, 282.965f, 399.793f, 282.965f, 393.816f, 288.086f)
                lineTo(314.012f, 356.488f)
                curveTo(310.473f, 359.523f, 308.438f, 363.949f, 308.438f, 368.609f)
                lineTo(308.438f, 513.531f)
                curveTo(308.438f, 522.348f, 315.586f, 529.492f, 324.398f, 529.492f)
                lineTo(484.004f, 529.492f)
                curveTo(492.82f, 529.492f, 499.965f, 522.348f, 499.965f, 513.531f)
                lineTo(499.965f, 368.609f)
                curveTo(499.965f, 363.949f, 497.93f, 359.523f, 494.391f, 356.488f)
                close()
                moveTo(373.039f, 263.852f)
                curveTo(390.973f, 248.48f, 417.434f, 248.48f, 435.363f, 263.852f)
                lineTo(515.168f, 332.254f)
                curveTo(525.777f, 341.352f, 531.887f, 354.629f, 531.887f, 368.609f)
                lineTo(531.887f, 513.531f)
                curveTo(531.887f, 539.977f, 510.449f, 561.414f, 484.004f, 561.414f)
                lineTo(324.398f, 561.414f)
                curveTo(297.953f, 561.414f, 276.516f, 539.977f, 276.516f, 513.531f)
                lineTo(276.516f, 368.609f)
                curveTo(276.516f, 354.629f, 282.625f, 341.352f, 293.238f, 332.254f)
                close()
                moveTo(373.039f, 263.852f)
            }
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(414.484f, 287.688f)
                    curveTo(408.543f, 282.684f, 399.863f, 282.684f, 393.922f, 287.688f)
                    lineTo(262.859f, 398.055f)
                    lineTo(242.297f, 373.641f)
                    lineTo(373.359f, 263.27f)
                    curveTo(391.184f, 248.262f, 417.223f, 248.262f, 435.047f, 263.27f)
                    lineTo(566.109f, 373.641f)
                    lineTo(545.551f, 398.055f)
                    close()
                    moveTo(414.484f, 287.688f)
                }
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd) {
                    moveTo(396.223f, 433.73f)
                    curveTo(387.406f, 433.73f, 380.262f, 440.875f, 380.262f, 449.691f)
                    lineTo(380.262f, 513.531f)
                    lineTo(348.34f, 513.531f)
                    lineTo(348.34f, 449.691f)
                    curveTo(348.34f, 423.246f, 369.777f, 401.809f, 396.223f, 401.809f)
                    lineTo(412.184f, 401.809f)
                    curveTo(438.629f, 401.809f, 460.063f, 423.246f, 460.063f, 449.691f)
                    lineTo(460.063f, 513.531f)
                    lineTo(428.145f, 513.531f)
                    lineTo(428.145f, 449.691f)
                    curveTo(428.145f, 440.875f, 420.996f, 433.73f, 412.184f, 433.73f)
                    close()
                    moveTo(396.223f, 433.73f)
                }
            }
        }
        .build()
        return _home!!
    }

private var _home: ImageVector? = null
