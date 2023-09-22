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

public val Rinfo.Notificationsolid: ImageVector
    get() {
        if (_notificationsolid != null) {
            return _notificationsolid!!
        }
        _notificationsolid = Builder(name = "Notificationsolid", defaultWidth = 1080.0.dp,
                defaultHeight = 1080.0.dp, viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(371.898f, 525.723f)
                lineTo(371.898f, 535.336f)
                curveTo(371.898f, 552.863f, 386.031f, 567.0f, 403.559f, 567.0f)
                lineTo(408.082f, 567.0f)
                curveTo(425.605f, 567.0f, 439.742f, 552.863f, 439.742f, 535.336f)
                lineTo(439.742f, 525.723f)
                close()
                moveTo(371.898f, 525.723f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(554.508f, 471.441f)
                curveTo(548.855f, 461.262f, 538.113f, 454.477f, 526.242f, 454.477f)
                curveTo(512.105f, 454.477f, 500.234f, 443.168f, 500.234f, 428.465f)
                lineTo(500.234f, 357.785f)
                curveTo(500.234f, 315.379f, 472.531f, 278.059f, 433.523f, 265.617f)
                curveTo(433.523f, 253.18f, 423.344f, 243.0f, 410.344f, 243.0f)
                lineTo(399.602f, 243.0f)
                curveTo(387.164f, 243.0f, 376.984f, 253.18f, 376.422f, 265.051f)
                curveTo(337.41f, 276.926f, 309.707f, 313.113f, 309.707f, 355.523f)
                lineTo(309.707f, 429.031f)
                curveTo(309.707f, 443.168f, 298.402f, 455.043f, 283.703f, 455.043f)
                lineTo(280.309f, 455.043f)
                curveTo(271.262f, 455.043f, 263.348f, 459.566f, 258.262f, 466.918f)
                curveTo(250.91f, 478.227f, 249.781f, 491.23f, 255.434f, 501.973f)
                curveTo(261.086f, 512.152f, 271.828f, 518.938f, 283.703f, 518.938f)
                lineTo(528.504f, 518.938f)
                curveTo(537.547f, 518.938f, 545.465f, 514.414f, 550.551f, 507.063f)
                curveTo(559.031f, 495.188f, 560.164f, 482.184f, 554.508f, 471.441f)
                close()
                moveTo(554.508f, 471.441f)
            }
        }
        .build()
        return _notificationsolid!!
    }

private var _notificationsolid: ImageVector? = null
