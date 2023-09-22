package com.danotech.rinfo.ui.components.rinfo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.components.Rinfo

public val Rinfo.Notification: ImageVector
    get() {
        if (_notification != null) {
            return _notification!!
        }
        _notification = Builder(name = "Notification", defaultWidth = 1080.0.dp, defaultHeight =
                1080.0.dp, viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(556.742f, 476.074f)
                curveTo(550.992f, 465.715f, 540.063f, 458.809f, 527.984f, 458.809f)
                curveTo(513.605f, 458.809f, 501.527f, 447.297f, 501.527f, 432.336f)
                lineTo(501.527f, 360.398f)
                curveTo(501.527f, 333.926f, 490.602f, 308.031f, 471.621f, 289.039f)
                curveTo(460.691f, 278.105f, 448.039f, 270.625f, 434.234f, 266.02f)
                curveTo(434.234f, 253.359f, 423.883f, 243.0f, 410.652f, 243.0f)
                lineTo(399.727f, 243.0f)
                curveTo(387.074f, 243.0f, 376.719f, 253.359f, 376.145f, 265.445f)
                curveTo(337.035f, 277.531f, 308.277f, 314.359f, 308.277f, 357.523f)
                lineTo(308.277f, 432.336f)
                curveTo(308.277f, 446.723f, 296.773f, 458.809f, 281.82f, 458.809f)
                lineTo(278.371f, 458.809f)
                curveTo(269.168f, 458.809f, 261.113f, 463.41f, 255.938f, 470.895f)
                curveTo(248.461f, 482.402f, 247.313f, 495.641f, 253.063f, 506.574f)
                curveTo(258.816f, 516.934f, 269.742f, 523.84f, 281.82f, 523.84f)
                lineTo(361.191f, 523.84f)
                lineTo(361.191f, 526.715f)
                curveTo(361.191f, 549.16f, 379.02f, 567.0f, 401.453f, 567.0f)
                lineTo(407.203f, 567.0f)
                curveTo(429.633f, 567.0f, 447.465f, 549.16f, 447.465f, 526.715f)
                lineTo(447.465f, 523.84f)
                lineTo(530.285f, 523.84f)
                curveTo(539.488f, 523.84f, 547.539f, 519.234f, 552.715f, 511.754f)
                curveTo(561.344f, 500.82f, 562.492f, 487.008f, 556.742f, 476.074f)
                close()
                moveTo(437.109f, 526.715f)
                curveTo(437.109f, 542.828f, 424.457f, 555.488f, 408.352f, 555.488f)
                lineTo(402.602f, 555.488f)
                curveTo(386.496f, 555.488f, 373.844f, 542.828f, 373.844f, 526.715f)
                lineTo(373.844f, 526.141f)
                lineTo(437.109f, 526.141f)
                close()
                moveTo(389.375f, 261.992f)
                curveTo(391.098f, 257.961f, 395.125f, 254.512f, 400.301f, 254.512f)
                lineTo(411.23f, 254.512f)
                curveTo(416.406f, 254.512f, 420.43f, 257.387f, 422.156f, 261.992f)
                lineTo(414.68f, 261.992f)
                curveTo(410.652f, 261.992f, 407.203f, 261.414f, 403.754f, 261.414f)
                curveTo(401.453f, 261.414f, 399.152f, 261.414f, 397.426f, 261.992f)
                close()
                moveTo(544.664f, 505.422f)
                curveTo(541.789f, 509.453f, 537.188f, 512.328f, 532.012f, 512.328f)
                lineTo(282.973f, 512.328f)
                curveTo(275.492f, 512.328f, 268.016f, 508.301f, 264.566f, 501.395f)
                curveTo(260.539f, 493.914f, 261.691f, 485.281f, 266.867f, 477.223f)
                curveTo(269.742f, 473.195f, 274.344f, 470.316f, 279.52f, 470.316f)
                lineTo(282.973f, 470.316f)
                curveTo(303.676f, 470.316f, 320.93f, 453.629f, 320.93f, 432.336f)
                lineTo(320.93f, 357.523f)
                curveTo(320.93f, 313.785f, 354.863f, 276.953f, 397.426f, 272.926f)
                lineTo(414.105f, 272.926f)
                curveTo(432.508f, 274.652f, 450.34f, 283.285f, 464.145f, 296.52f)
                curveTo(480.82f, 313.211f, 490.602f, 336.23f, 490.602f, 359.25f)
                lineTo(490.602f, 431.762f)
                curveTo(490.602f, 452.477f, 507.277f, 469.742f, 528.559f, 469.742f)
                curveTo(536.035f, 469.742f, 543.512f, 473.77f, 546.965f, 480.676f)
                curveTo(550.414f, 488.734f, 549.84f, 497.941f, 544.664f, 505.422f)
                close()
                moveTo(544.664f, 505.422f)
            }
        }
        .build()
        return _notification!!
    }

private var _notification: ImageVector? = null
