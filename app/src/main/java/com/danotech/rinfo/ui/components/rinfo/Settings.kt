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

public val Rinfo.Settings: ImageVector
    get() {
        if (_settings != null) {
            return _settings!!
        }
        _settings = Builder(name = "Settings", defaultWidth = 1080.0.dp, defaultHeight = 1080.0.dp,
                viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(255.938f, 470.008f)
                lineTo(274.438f, 501.992f)
                curveTo(284.578f, 519.445f, 306.941f, 525.508f, 324.398f, 515.473f)
                lineTo(334.117f, 509.828f)
                curveTo(339.344f, 513.277f, 344.777f, 516.414f, 350.422f, 519.133f)
                lineTo(350.422f, 530.418f)
                curveTo(350.422f, 550.59f, 366.832f, 567.0f, 387.004f, 567.0f)
                lineTo(424.0f, 567.0f)
                curveTo(444.172f, 567.0f, 460.582f, 550.59f, 460.582f, 530.418f)
                lineTo(460.582f, 519.133f)
                curveTo(466.227f, 516.414f, 471.66f, 513.277f, 476.887f, 509.828f)
                lineTo(486.605f, 515.473f)
                curveTo(504.059f, 525.508f, 526.426f, 519.551f, 536.566f, 501.992f)
                lineTo(555.063f, 470.008f)
                curveTo(558.41f, 464.262f, 559.977f, 457.988f, 559.977f, 451.719f)
                curveTo(559.977f, 439.074f, 553.391f, 426.738f, 541.688f, 419.945f)
                lineTo(531.965f, 414.406f)
                curveTo(532.176f, 411.168f, 532.281f, 408.031f, 532.281f, 405.0f)
                curveTo(532.281f, 401.969f, 532.176f, 398.832f, 531.965f, 395.594f)
                lineTo(541.688f, 390.055f)
                curveTo(559.141f, 379.914f, 565.098f, 357.551f, 555.063f, 339.992f)
                lineTo(536.566f, 308.008f)
                curveTo(531.652f, 299.543f, 523.813f, 293.48f, 514.301f, 290.867f)
                curveTo(504.895f, 288.359f, 495.07f, 289.719f, 486.605f, 294.527f)
                lineTo(476.887f, 300.172f)
                curveTo(471.66f, 296.723f, 466.227f, 293.586f, 460.582f, 290.867f)
                lineTo(460.582f, 279.582f)
                curveTo(460.582f, 259.41f, 444.172f, 243.0f, 424.0f, 243.0f)
                lineTo(387.0f, 243.0f)
                curveTo(366.832f, 243.0f, 350.422f, 259.41f, 350.422f, 279.582f)
                lineTo(350.422f, 290.867f)
                curveTo(344.777f, 293.586f, 339.344f, 296.723f, 334.117f, 300.172f)
                lineTo(324.398f, 294.527f)
                curveTo(315.93f, 289.719f, 306.105f, 288.359f, 296.699f, 290.867f)
                curveTo(287.188f, 293.48f, 279.352f, 299.543f, 274.438f, 308.008f)
                lineTo(255.938f, 339.992f)
                curveTo(245.906f, 357.551f, 251.863f, 379.914f, 269.316f, 390.055f)
                lineTo(279.039f, 395.594f)
                curveTo(278.828f, 398.832f, 278.723f, 401.969f, 278.723f, 405.0f)
                curveTo(278.723f, 408.031f, 278.828f, 411.168f, 279.039f, 414.406f)
                lineTo(269.316f, 419.945f)
                curveTo(251.863f, 430.082f, 245.906f, 452.449f, 255.938f, 470.008f)
                close()
                moveTo(405.523f, 336.93f)
                curveTo(443.105f, 336.93f, 473.594f, 367.414f, 473.594f, 405.0f)
                curveTo(473.594f, 442.582f, 443.105f, 473.07f, 405.523f, 473.07f)
                curveTo(367.938f, 473.07f, 337.449f, 442.582f, 337.449f, 405.0f)
                curveTo(337.449f, 367.414f, 367.938f, 336.93f, 405.523f, 336.93f)
                close()
                moveTo(405.523f, 336.93f)
            }
        }
        .build()
        return _settings!!
    }

private var _settings: ImageVector? = null
