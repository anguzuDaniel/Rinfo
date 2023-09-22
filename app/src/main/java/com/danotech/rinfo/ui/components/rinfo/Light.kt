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

public val Rinfo.Light: ImageVector
    get() {
        if (_light != null) {
            return _light!!
        }
        _light = Builder(name = "Light", defaultWidth = 1080.0.dp, defaultHeight = 1080.0.dp,
                viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(486.0f, 404.992f)
                curveTo(486.0f, 360.324f, 449.66f, 323.992f, 405.0f, 323.992f)
                curveTo(360.34f, 323.992f, 324.0f, 360.324f, 324.0f, 404.992f)
                curveTo(324.0f, 449.66f, 360.34f, 485.992f, 405.0f, 485.992f)
                curveTo(449.66f, 485.992f, 486.0f, 449.66f, 486.0f, 404.992f)
                close()
                moveTo(405.0f, 453.594f)
                curveTo(378.199f, 453.594f, 356.398f, 431.793f, 356.398f, 404.992f)
                curveTo(356.398f, 378.191f, 378.199f, 356.391f, 405.0f, 356.391f)
                curveTo(431.801f, 356.391f, 453.602f, 378.191f, 453.602f, 404.992f)
                curveTo(453.602f, 431.793f, 431.801f, 453.594f, 405.0f, 453.594f)
                close()
                moveTo(405.0f, 453.594f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(421.199f, 275.398f)
                lineTo(421.199f, 259.199f)
                curveTo(421.199f, 250.254f, 413.945f, 243.0f, 405.0f, 243.0f)
                curveTo(396.055f, 243.0f, 388.801f, 250.254f, 388.801f, 259.199f)
                lineTo(388.801f, 275.398f)
                curveTo(388.801f, 284.34f, 396.055f, 291.602f, 405.0f, 291.602f)
                curveTo(413.945f, 291.602f, 421.199f, 284.34f, 421.199f, 275.398f)
                close()
                moveTo(421.199f, 275.398f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(519.555f, 290.445f)
                curveTo(513.227f, 284.117f, 502.961f, 284.117f, 496.648f, 290.445f)
                lineTo(485.191f, 301.898f)
                curveTo(478.863f, 308.227f, 478.863f, 318.488f, 485.191f, 324.809f)
                curveTo(488.348f, 327.973f, 492.504f, 329.555f, 496.648f, 329.555f)
                curveTo(500.793f, 329.555f, 504.938f, 327.973f, 508.102f, 324.809f)
                lineTo(519.555f, 313.352f)
                curveTo(525.883f, 307.031f, 525.883f, 296.773f, 519.555f, 290.445f)
                close()
                moveTo(519.555f, 290.445f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(550.801f, 388.801f)
                lineTo(534.602f, 388.801f)
                curveTo(525.66f, 388.801f, 518.398f, 396.055f, 518.398f, 405.0f)
                curveTo(518.398f, 413.945f, 525.66f, 421.199f, 534.602f, 421.199f)
                lineTo(550.801f, 421.199f)
                curveTo(559.746f, 421.199f, 567.0f, 413.945f, 567.0f, 405.0f)
                curveTo(567.0f, 396.055f, 559.746f, 388.801f, 550.801f, 388.801f)
                close()
                moveTo(550.801f, 388.801f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(496.648f, 519.555f)
                curveTo(499.805f, 522.719f, 503.957f, 524.301f, 508.102f, 524.301f)
                curveTo(512.246f, 524.301f, 516.391f, 522.719f, 519.555f, 519.555f)
                curveTo(525.883f, 513.227f, 525.883f, 502.969f, 519.555f, 496.648f)
                lineTo(508.102f, 485.191f)
                curveTo(501.773f, 478.863f, 491.504f, 478.863f, 485.191f, 485.191f)
                curveTo(478.863f, 491.512f, 478.863f, 501.773f, 485.191f, 508.102f)
                close()
                moveTo(496.648f, 519.555f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(388.801f, 534.602f)
                lineTo(388.801f, 550.801f)
                curveTo(388.801f, 559.746f, 396.055f, 567.0f, 405.0f, 567.0f)
                curveTo(413.945f, 567.0f, 421.199f, 559.746f, 421.199f, 550.801f)
                lineTo(421.199f, 534.602f)
                curveTo(421.199f, 525.66f, 413.945f, 518.398f, 405.0f, 518.398f)
                curveTo(396.055f, 518.398f, 388.801f, 525.66f, 388.801f, 534.602f)
                close()
                moveTo(388.801f, 534.602f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(290.445f, 519.555f)
                curveTo(293.609f, 522.719f, 297.754f, 524.301f, 301.898f, 524.301f)
                curveTo(306.043f, 524.301f, 310.195f, 522.719f, 313.352f, 519.555f)
                lineTo(324.809f, 508.102f)
                curveTo(331.137f, 501.773f, 331.137f, 491.512f, 324.809f, 485.191f)
                curveTo(318.496f, 478.863f, 308.227f, 478.863f, 301.898f, 485.191f)
                lineTo(290.445f, 496.648f)
                curveTo(284.117f, 502.969f, 284.117f, 513.227f, 290.445f, 519.555f)
                close()
                moveTo(290.445f, 519.555f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(243.0f, 405.0f)
                curveTo(243.0f, 413.945f, 250.254f, 421.199f, 259.199f, 421.199f)
                lineTo(275.398f, 421.199f)
                curveTo(284.34f, 421.199f, 291.602f, 413.945f, 291.602f, 405.0f)
                curveTo(291.602f, 396.055f, 284.34f, 388.801f, 275.398f, 388.801f)
                lineTo(259.199f, 388.801f)
                curveTo(250.254f, 388.801f, 243.0f, 396.055f, 243.0f, 405.0f)
                close()
                moveTo(243.0f, 405.0f)
            }
            path(fill = SolidColor(Color(0xFF333333)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(324.809f, 324.809f)
                curveTo(331.137f, 318.488f, 331.137f, 308.227f, 324.809f, 301.898f)
                lineTo(313.352f, 290.445f)
                curveTo(307.039f, 284.117f, 296.773f, 284.117f, 290.445f, 290.445f)
                curveTo(284.117f, 296.773f, 284.117f, 307.031f, 290.445f, 313.352f)
                lineTo(301.898f, 324.809f)
                curveTo(305.063f, 327.973f, 309.207f, 329.555f, 313.352f, 329.555f)
                curveTo(317.496f, 329.555f, 321.652f, 327.973f, 324.809f, 324.809f)
                close()
                moveTo(324.809f, 324.809f)
            }
        }
        .build()
        return _light!!
    }

private var _light: ImageVector? = null
