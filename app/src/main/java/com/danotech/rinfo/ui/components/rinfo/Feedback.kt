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

public val Rinfo.Feedback: ImageVector
    get() {
        if (_feedback != null) {
            return _feedback!!
        }
        _feedback = Builder(name = "Feedback", defaultWidth = 1080.0.dp, defaultHeight = 1080.0.dp,
                viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            group {
                path(fill = SolidColor(Color(0xFF100f0d)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(526.648f, 393.48f)
                    curveTo(525.691f, 393.094f, 524.617f, 393.133f, 523.695f, 393.602f)
                    curveTo(514.074f, 398.48f, 503.469f, 401.621f, 492.152f, 402.953f)
                    curveTo(474.047f, 387.633f, 448.266f, 378.039f, 419.695f, 378.039f)
                    curveTo(414.102f, 378.039f, 408.613f, 378.41f, 403.273f, 379.117f)
                    curveTo(412.152f, 368.133f, 416.949f, 355.293f, 416.949f, 342.086f)
                    curveTo(416.949f, 331.973f, 414.168f, 322.359f, 409.18f, 313.707f)
                    curveTo(422.863f, 293.473f, 450.379f, 280.504f, 480.0f, 280.504f)
                    curveTo(524.008f, 280.504f, 559.816f, 308.133f, 559.816f, 342.086f)
                    curveTo(559.816f, 355.43f, 554.355f, 368.133f, 544.027f, 378.82f)
                    curveTo(543.105f, 379.773f, 542.789f, 381.16f, 543.203f, 382.418f)
                    lineTo(549.98f, 402.934f)
                    close()
                    moveTo(419.695f, 527.316f)
                    curveTo(401.746f, 527.316f, 384.352f, 523.313f, 369.395f, 515.734f)
                    curveTo(368.891f, 515.477f, 368.336f, 515.348f, 367.781f, 515.348f)
                    curveTo(367.324f, 515.348f, 366.875f, 515.434f, 366.441f, 515.605f)
                    lineTo(338.672f, 526.859f)
                    lineTo(346.785f, 502.289f)
                    curveTo(347.203f, 501.031f, 346.883f, 499.648f, 345.965f, 498.695f)
                    curveTo(334.035f, 486.348f, 327.727f, 471.664f, 327.727f, 456.242f)
                    curveTo(327.727f, 417.055f, 368.984f, 385.172f, 419.695f, 385.172f)
                    curveTo(447.156f, 385.172f, 471.84f, 394.523f, 488.703f, 409.324f)
                    curveTo(488.77f, 409.379f, 488.828f, 409.438f, 488.895f, 409.488f)
                    curveTo(503.063f, 421.992f, 511.66f, 438.359f, 511.66f, 456.242f)
                    curveTo(511.66f, 495.438f, 470.402f, 527.316f, 419.695f, 527.316f)
                    close()
                    moveTo(286.305f, 393.602f)
                    curveTo(285.383f, 393.137f, 284.305f, 393.094f, 283.352f, 393.48f)
                    lineTo(260.02f, 402.934f)
                    lineTo(266.797f, 382.418f)
                    curveTo(267.211f, 381.16f, 266.895f, 379.773f, 265.973f, 378.82f)
                    curveTo(255.645f, 368.133f, 250.184f, 355.43f, 250.184f, 342.086f)
                    curveTo(250.184f, 308.133f, 285.992f, 280.504f, 330.0f, 280.504f)
                    curveTo(374.012f, 280.504f, 409.816f, 308.133f, 409.816f, 342.086f)
                    curveTo(409.816f, 356.375f, 403.316f, 370.246f, 391.523f, 381.262f)
                    curveTo(374.898f, 385.156f, 360.086f, 392.41f, 348.352f, 402.02f)
                    curveTo(342.367f, 403.109f, 336.199f, 403.668f, 330.0f, 403.668f)
                    curveTo(314.406f, 403.668f, 299.297f, 400.188f, 286.305f, 393.602f)
                    close()
                    moveTo(550.641f, 382.191f)
                    curveTo(561.32f, 370.477f, 566.949f, 356.652f, 566.949f, 342.086f)
                    curveTo(566.949f, 304.195f, 527.945f, 273.371f, 480.0f, 273.371f)
                    curveTo(448.734f, 273.371f, 420.527f, 286.254f, 404.961f, 307.309f)
                    curveTo(389.832f, 287.016f, 361.898f, 273.371f, 330.0f, 273.371f)
                    curveTo(282.055f, 273.371f, 243.051f, 304.195f, 243.051f, 342.086f)
                    curveTo(243.051f, 356.652f, 248.68f, 370.477f, 259.359f, 382.191f)
                    lineTo(250.828f, 408.016f)
                    curveTo(250.391f, 409.336f, 250.762f, 410.789f, 251.777f, 411.738f)
                    curveTo(252.453f, 412.367f, 253.328f, 412.703f, 254.215f, 412.703f)
                    curveTo(254.668f, 412.703f, 255.121f, 412.617f, 255.555f, 412.441f)
                    lineTo(284.543f, 400.695f)
                    curveTo(298.195f, 407.309f, 313.871f, 410.801f, 330.0f, 410.801f)
                    curveTo(333.168f, 410.801f, 336.328f, 410.66f, 339.465f, 410.391f)
                    curveTo(327.602f, 423.285f, 320.594f, 439.129f, 320.594f, 456.242f)
                    curveTo(320.594f, 472.891f, 327.07f, 488.691f, 339.348f, 502.07f)
                    lineTo(329.477f, 531.941f)
                    curveTo(329.043f, 533.266f, 329.414f, 534.715f, 330.43f, 535.668f)
                    curveTo(331.102f, 536.297f, 331.977f, 536.629f, 332.867f, 536.629f)
                    curveTo(333.32f, 536.629f, 333.773f, 536.543f, 334.207f, 536.367f)
                    lineTo(367.633f, 522.82f)
                    curveTo(383.25f, 530.438f, 401.211f, 534.453f, 419.695f, 534.453f)
                    curveTo(474.336f, 534.453f, 518.793f, 499.367f, 518.793f, 456.242f)
                    curveTo(518.793f, 438.594f, 511.344f, 422.293f, 498.801f, 409.195f)
                    curveTo(508.227f, 407.563f, 517.164f, 404.719f, 525.457f, 400.695f)
                    lineTo(554.445f, 412.441f)
                    curveTo(554.879f, 412.617f, 555.332f, 412.703f, 555.785f, 412.703f)
                    curveTo(556.672f, 412.703f, 557.547f, 412.367f, 558.223f, 411.738f)
                    curveTo(559.238f, 410.789f, 559.609f, 409.336f, 559.172f, 408.016f)
                    lineTo(550.641f, 382.191f)
                }
                path(fill = SolidColor(Color(0xFF100f0d)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(460.277f, 453.316f)
                    curveTo(460.223f, 453.469f, 460.176f, 453.656f, 460.141f, 453.816f)
                    curveTo(460.098f, 454.023f, 455.453f, 474.344f, 419.695f, 493.348f)
                    curveTo(383.934f, 474.344f, 379.289f, 454.023f, 379.254f, 453.844f)
                    curveTo(379.215f, 453.664f, 379.168f, 453.488f, 379.109f, 453.316f)
                    curveTo(378.371f, 451.262f, 377.996f, 449.102f, 377.996f, 446.887f)
                    curveTo(377.996f, 436.375f, 386.547f, 427.824f, 397.059f, 427.824f)
                    curveTo(407.574f, 427.824f, 416.129f, 436.375f, 416.129f, 446.887f)
                    curveTo(416.129f, 448.859f, 417.723f, 450.457f, 419.695f, 450.457f)
                    curveTo(421.664f, 450.457f, 423.262f, 448.859f, 423.262f, 446.887f)
                    curveTo(423.262f, 436.375f, 431.813f, 427.824f, 442.324f, 427.824f)
                    curveTo(452.836f, 427.824f, 461.391f, 436.375f, 461.391f, 446.887f)
                    curveTo(461.391f, 449.102f, 461.016f, 451.262f, 460.277f, 453.316f)
                    close()
                    moveTo(442.324f, 420.691f)
                    curveTo(432.684f, 420.691f, 424.242f, 425.922f, 419.695f, 433.699f)
                    curveTo(415.145f, 425.922f, 406.707f, 420.691f, 397.059f, 420.691f)
                    curveTo(382.617f, 420.691f, 370.863f, 432.441f, 370.863f, 446.887f)
                    curveTo(370.863f, 449.844f, 371.352f, 452.742f, 372.316f, 455.508f)
                    curveTo(373.016f, 458.488f, 379.391f, 480.574f, 418.055f, 500.543f)
                    curveTo(418.57f, 500.809f, 419.133f, 500.941f, 419.695f, 500.941f)
                    curveTo(420.258f, 500.941f, 420.816f, 500.809f, 421.332f, 500.543f)
                    curveTo(459.996f, 480.574f, 466.375f, 458.488f, 467.074f, 455.508f)
                    curveTo(468.035f, 452.742f, 468.523f, 449.844f, 468.523f, 446.887f)
                    curveTo(468.523f, 432.441f, 456.77f, 420.691f, 442.324f, 420.691f)
                }
                path(fill = SolidColor(Color(0xFF100f0d)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(338.754f, 359.063f)
                    lineTo(338.754f, 342.344f)
                    curveTo(338.754f, 336.441f, 343.555f, 331.637f, 349.461f, 331.637f)
                    lineTo(360.469f, 331.637f)
                    curveTo(364.379f, 331.637f, 367.563f, 334.824f, 367.563f, 338.734f)
                    lineTo(367.563f, 362.672f)
                    curveTo(367.563f, 366.586f, 364.379f, 369.766f, 360.469f, 369.766f)
                    lineTo(349.461f, 369.766f)
                    curveTo(343.555f, 369.766f, 338.754f, 364.965f, 338.754f, 359.063f)
                    close()
                    moveTo(355.898f, 376.902f)
                    lineTo(360.469f, 376.902f)
                    curveTo(368.313f, 376.902f, 374.695f, 370.516f, 374.695f, 362.672f)
                    lineTo(374.695f, 338.734f)
                    curveTo(374.695f, 330.887f, 368.313f, 324.504f, 360.469f, 324.504f)
                    lineTo(347.531f, 324.504f)
                    lineTo(352.063f, 308.195f)
                    curveTo(353.063f, 304.594f, 352.34f, 300.824f, 350.078f, 297.848f)
                    curveTo(347.816f, 294.875f, 344.375f, 293.168f, 340.477f, 293.168f)
                    curveTo(335.879f, 293.168f, 331.656f, 295.863f, 329.723f, 300.035f)
                    lineTo(322.906f, 314.727f)
                    curveTo(320.148f, 320.668f, 314.133f, 324.504f, 307.586f, 324.504f)
                    lineTo(288.871f, 324.504f)
                    curveTo(286.902f, 324.504f, 285.305f, 326.102f, 285.305f, 328.07f)
                    curveTo(285.305f, 330.043f, 286.902f, 331.637f, 288.871f, 331.637f)
                    lineTo(307.586f, 331.637f)
                    curveTo(316.902f, 331.637f, 325.453f, 326.18f, 329.375f, 317.73f)
                    lineTo(336.195f, 303.035f)
                    curveTo(336.965f, 301.375f, 338.648f, 300.301f, 340.641f, 300.301f)
                    curveTo(342.129f, 300.301f, 343.5f, 300.98f, 344.398f, 302.164f)
                    curveTo(345.301f, 303.348f, 345.586f, 304.852f, 345.191f, 306.285f)
                    lineTo(339.406f, 327.117f)
                    curveTo(339.352f, 327.309f, 339.32f, 327.5f, 339.301f, 327.691f)
                    curveTo(334.66f, 330.918f, 331.617f, 336.281f, 331.617f, 342.344f)
                    lineTo(331.617f, 359.063f)
                    curveTo(331.617f, 363.074f, 332.949f, 366.785f, 335.195f, 369.766f)
                    lineTo(288.871f, 369.766f)
                    curveTo(286.902f, 369.766f, 285.305f, 371.363f, 285.305f, 373.332f)
                    curveTo(285.305f, 375.305f, 286.902f, 376.902f, 288.871f, 376.902f)
                    lineTo(355.898f, 376.902f)
                }
                path(fill = SolidColor(Color(0xFF100f0d)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(445.156f, 336.18f)
                    curveTo(436.547f, 336.18f, 436.531f, 349.559f, 445.156f, 349.559f)
                    curveTo(453.762f, 349.559f, 453.773f, 336.18f, 445.156f, 336.18f)
                }
                path(fill = SolidColor(Color(0xFF100f0d)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(484.188f, 336.18f)
                    curveTo(475.582f, 336.18f, 475.566f, 349.559f, 484.188f, 349.559f)
                    curveTo(492.793f, 349.559f, 492.813f, 336.18f, 484.188f, 336.18f)
                }
                path(fill = SolidColor(Color(0xFF100f0d)), stroke = null, strokeLineWidth = 0.0f,
                        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                        pathFillType = NonZero) {
                    moveTo(523.223f, 336.18f)
                    curveTo(514.617f, 336.18f, 514.605f, 349.559f, 523.223f, 349.559f)
                    curveTo(531.832f, 349.559f, 531.844f, 336.18f, 523.223f, 336.18f)
                }
            }
        }
        .build()
        return _feedback!!
    }

private var _feedback: ImageVector? = null
