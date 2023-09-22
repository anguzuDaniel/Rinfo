package com.danotech.rinfo.ui.components.rinfo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.danotech.rinfo.ui.components.Rinfo

public val Rinfo.Homesolid: ImageVector
    get() {
        if (_homesolid != null) {
            return _homesolid!!
        }
        _homesolid = Builder(name = "Homesolid", defaultWidth = 1080.0.dp, defaultHeight =
                1080.0.dp, viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(557.266f, 388.633f)
                    lineTo(422.629f, 253.949f)
                    curveTo(412.895f, 244.207f, 397.105f, 244.207f, 387.371f, 253.949f)
                    lineTo(252.734f, 388.633f)
                    curveTo(242.996f, 398.375f, 242.996f, 414.164f, 252.734f, 423.906f)
                    curveTo(260.199f, 431.371f, 271.215f, 433.109f, 280.336f, 429.129f)
                    lineTo(280.336f, 528.484f)
                    curveTo(280.336f, 549.145f, 297.082f, 565.895f, 317.734f, 565.895f)
                    lineTo(492.266f, 565.895f)
                    curveTo(512.918f, 565.895f, 529.664f, 549.145f, 529.664f, 528.484f)
                    lineTo(529.664f, 429.129f)
                    curveTo(538.785f, 433.109f, 549.801f, 431.371f, 557.266f, 423.906f)
                    curveTo(567.004f, 414.164f, 567.004f, 398.375f, 557.266f, 388.633f)
                    close()
                    moveTo(448.633f, 516.012f)
                    curveTo(448.633f, 522.902f, 443.051f, 528.484f, 436.164f, 528.484f)
                    lineTo(373.836f, 528.484f)
                    curveTo(366.949f, 528.484f, 361.367f, 522.902f, 361.367f, 516.012f)
                    lineTo(361.367f, 441.188f)
                    curveTo(361.367f, 434.301f, 366.949f, 428.715f, 373.836f, 428.715f)
                    lineTo(436.164f, 428.715f)
                    curveTo(443.051f, 428.715f, 448.633f, 434.301f, 448.633f, 441.188f)
                    close()
                    moveTo(448.633f, 516.012f)
                }
            }
        }
        .build()
        return _homesolid!!
    }

private var _homesolid: ImageVector? = null
