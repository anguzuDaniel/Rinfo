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

public val Rinfo.Lightsolid: ImageVector
    get() {
        if (_lightsolid != null) {
            return _lightsolid!!
        }
        _lightsolid = Builder(name = "Lightsolid", defaultWidth = 1080.0.dp, defaultHeight =
                1080.0.dp, viewportWidth = 810.0f, viewportHeight = 810.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(486.0f, 405.0f)
                curveTo(486.0f, 406.324f, 485.969f, 407.652f, 485.902f, 408.973f)
                curveTo(485.836f, 410.297f, 485.738f, 411.621f, 485.609f, 412.938f)
                curveTo(485.48f, 414.258f, 485.316f, 415.574f, 485.125f, 416.887f)
                curveTo(484.93f, 418.195f, 484.703f, 419.504f, 484.445f, 420.801f)
                curveTo(484.184f, 422.102f, 483.895f, 423.395f, 483.574f, 424.68f)
                curveTo(483.25f, 425.969f, 482.898f, 427.246f, 482.512f, 428.512f)
                curveTo(482.129f, 429.781f, 481.711f, 431.039f, 481.266f, 432.289f)
                curveTo(480.82f, 433.535f, 480.34f, 434.773f, 479.836f, 435.996f)
                curveTo(479.328f, 437.223f, 478.789f, 438.434f, 478.223f, 439.633f)
                curveTo(477.656f, 440.832f, 477.059f, 442.016f, 476.438f, 443.184f)
                curveTo(475.813f, 444.352f, 475.156f, 445.504f, 474.477f, 446.641f)
                curveTo(473.793f, 447.781f, 473.086f, 448.898f, 472.348f, 450.0f)
                curveTo(471.613f, 451.102f, 470.848f, 452.188f, 470.059f, 453.25f)
                curveTo(469.27f, 454.316f, 468.453f, 455.359f, 467.613f, 456.387f)
                curveTo(466.773f, 457.41f, 465.906f, 458.414f, 465.016f, 459.395f)
                curveTo(464.125f, 460.379f, 463.215f, 461.34f, 462.277f, 462.277f)
                curveTo(461.34f, 463.215f, 460.379f, 464.125f, 459.395f, 465.016f)
                curveTo(458.414f, 465.906f, 457.41f, 466.773f, 456.387f, 467.613f)
                curveTo(455.359f, 468.453f, 454.316f, 469.27f, 453.25f, 470.059f)
                curveTo(452.188f, 470.848f, 451.102f, 471.613f, 450.0f, 472.348f)
                curveTo(448.898f, 473.086f, 447.777f, 473.793f, 446.641f, 474.477f)
                curveTo(445.504f, 475.156f, 444.352f, 475.813f, 443.184f, 476.438f)
                curveTo(442.016f, 477.059f, 440.832f, 477.656f, 439.633f, 478.223f)
                curveTo(438.434f, 478.789f, 437.223f, 479.328f, 435.996f, 479.836f)
                curveTo(434.773f, 480.34f, 433.535f, 480.82f, 432.289f, 481.266f)
                curveTo(431.039f, 481.711f, 429.781f, 482.129f, 428.512f, 482.512f)
                curveTo(427.246f, 482.898f, 425.969f, 483.25f, 424.68f, 483.574f)
                curveTo(423.395f, 483.895f, 422.102f, 484.184f, 420.801f, 484.445f)
                curveTo(419.504f, 484.703f, 418.195f, 484.93f, 416.887f, 485.125f)
                curveTo(415.574f, 485.316f, 414.258f, 485.48f, 412.938f, 485.609f)
                curveTo(411.621f, 485.738f, 410.297f, 485.836f, 408.973f, 485.902f)
                curveTo(407.652f, 485.969f, 406.324f, 486.0f, 405.0f, 486.0f)
                curveTo(403.676f, 486.0f, 402.348f, 485.969f, 401.027f, 485.902f)
                curveTo(399.703f, 485.836f, 398.379f, 485.738f, 397.063f, 485.609f)
                curveTo(395.742f, 485.48f, 394.426f, 485.316f, 393.113f, 485.125f)
                curveTo(391.805f, 484.93f, 390.496f, 484.703f, 389.199f, 484.445f)
                curveTo(387.898f, 484.184f, 386.605f, 483.895f, 385.32f, 483.574f)
                curveTo(384.031f, 483.25f, 382.754f, 482.898f, 381.488f, 482.512f)
                curveTo(380.219f, 482.129f, 378.961f, 481.711f, 377.711f, 481.266f)
                curveTo(376.465f, 480.82f, 375.227f, 480.34f, 374.004f, 479.836f)
                curveTo(372.777f, 479.328f, 371.566f, 478.789f, 370.367f, 478.223f)
                curveTo(369.168f, 477.656f, 367.984f, 477.059f, 366.816f, 476.438f)
                curveTo(365.648f, 475.813f, 364.496f, 475.156f, 363.359f, 474.477f)
                curveTo(362.219f, 473.793f, 361.102f, 473.086f, 360.0f, 472.348f)
                curveTo(358.898f, 471.613f, 357.813f, 470.848f, 356.75f, 470.059f)
                curveTo(355.684f, 469.27f, 354.641f, 468.453f, 353.613f, 467.613f)
                curveTo(352.59f, 466.773f, 351.586f, 465.906f, 350.605f, 465.016f)
                curveTo(349.621f, 464.125f, 348.66f, 463.215f, 347.723f, 462.277f)
                curveTo(346.785f, 461.34f, 345.875f, 460.379f, 344.984f, 459.395f)
                curveTo(344.094f, 458.414f, 343.227f, 457.41f, 342.387f, 456.387f)
                curveTo(341.547f, 455.359f, 340.73f, 454.316f, 339.941f, 453.25f)
                curveTo(339.152f, 452.188f, 338.387f, 451.102f, 337.652f, 450.0f)
                curveTo(336.914f, 448.898f, 336.207f, 447.781f, 335.523f, 446.641f)
                curveTo(334.844f, 445.504f, 334.188f, 444.352f, 333.563f, 443.184f)
                curveTo(332.941f, 442.016f, 332.344f, 440.832f, 331.777f, 439.633f)
                curveTo(331.211f, 438.434f, 330.672f, 437.223f, 330.164f, 435.996f)
                curveTo(329.66f, 434.773f, 329.18f, 433.535f, 328.734f, 432.289f)
                curveTo(328.289f, 431.039f, 327.871f, 429.781f, 327.488f, 428.512f)
                curveTo(327.102f, 427.246f, 326.75f, 425.969f, 326.426f, 424.68f)
                curveTo(326.105f, 423.395f, 325.816f, 422.102f, 325.555f, 420.801f)
                curveTo(325.297f, 419.504f, 325.07f, 418.195f, 324.875f, 416.887f)
                curveTo(324.684f, 415.574f, 324.52f, 414.258f, 324.391f, 412.938f)
                curveTo(324.262f, 411.621f, 324.164f, 410.297f, 324.098f, 408.973f)
                curveTo(324.031f, 407.652f, 324.0f, 406.324f, 324.0f, 405.0f)
                curveTo(324.0f, 403.676f, 324.031f, 402.348f, 324.098f, 401.027f)
                curveTo(324.164f, 399.703f, 324.262f, 398.379f, 324.391f, 397.063f)
                curveTo(324.52f, 395.742f, 324.684f, 394.426f, 324.875f, 393.113f)
                curveTo(325.07f, 391.805f, 325.297f, 390.496f, 325.555f, 389.199f)
                curveTo(325.816f, 387.898f, 326.105f, 386.605f, 326.426f, 385.32f)
                curveTo(326.75f, 384.031f, 327.102f, 382.754f, 327.488f, 381.488f)
                curveTo(327.871f, 380.219f, 328.289f, 378.961f, 328.734f, 377.711f)
                curveTo(329.18f, 376.465f, 329.66f, 375.227f, 330.164f, 374.004f)
                curveTo(330.672f, 372.777f, 331.211f, 371.566f, 331.777f, 370.367f)
                curveTo(332.344f, 369.168f, 332.941f, 367.984f, 333.563f, 366.816f)
                curveTo(334.188f, 365.648f, 334.844f, 364.496f, 335.523f, 363.359f)
                curveTo(336.207f, 362.219f, 336.914f, 361.102f, 337.652f, 360.0f)
                curveTo(338.387f, 358.898f, 339.152f, 357.813f, 339.941f, 356.75f)
                curveTo(340.73f, 355.684f, 341.547f, 354.641f, 342.387f, 353.613f)
                curveTo(343.227f, 352.59f, 344.094f, 351.586f, 344.984f, 350.605f)
                curveTo(345.875f, 349.621f, 346.785f, 348.66f, 347.723f, 347.723f)
                curveTo(348.66f, 346.785f, 349.621f, 345.875f, 350.605f, 344.984f)
                curveTo(351.586f, 344.094f, 352.59f, 343.227f, 353.613f, 342.387f)
                curveTo(354.641f, 341.547f, 355.684f, 340.73f, 356.75f, 339.941f)
                curveTo(357.813f, 339.152f, 358.898f, 338.387f, 360.0f, 337.652f)
                curveTo(361.102f, 336.914f, 362.219f, 336.207f, 363.359f, 335.523f)
                curveTo(364.496f, 334.844f, 365.648f, 334.188f, 366.816f, 333.563f)
                curveTo(367.984f, 332.941f, 369.168f, 332.344f, 370.367f, 331.777f)
                curveTo(371.566f, 331.211f, 372.777f, 330.672f, 374.004f, 330.164f)
                curveTo(375.227f, 329.66f, 376.465f, 329.18f, 377.711f, 328.734f)
                curveTo(378.961f, 328.289f, 380.219f, 327.871f, 381.488f, 327.488f)
                curveTo(382.754f, 327.102f, 384.031f, 326.75f, 385.32f, 326.426f)
                curveTo(386.605f, 326.105f, 387.898f, 325.816f, 389.199f, 325.555f)
                curveTo(390.496f, 325.297f, 391.805f, 325.07f, 393.113f, 324.875f)
                curveTo(394.426f, 324.684f, 395.742f, 324.52f, 397.063f, 324.391f)
                curveTo(398.379f, 324.262f, 399.703f, 324.164f, 401.027f, 324.098f)
                curveTo(402.348f, 324.031f, 403.676f, 324.0f, 405.0f, 324.0f)
                curveTo(406.324f, 324.0f, 407.652f, 324.031f, 408.973f, 324.098f)
                curveTo(410.297f, 324.164f, 411.621f, 324.262f, 412.938f, 324.391f)
                curveTo(414.258f, 324.52f, 415.574f, 324.684f, 416.887f, 324.875f)
                curveTo(418.195f, 325.07f, 419.504f, 325.297f, 420.801f, 325.555f)
                curveTo(422.102f, 325.816f, 423.395f, 326.105f, 424.68f, 326.426f)
                curveTo(425.969f, 326.75f, 427.246f, 327.102f, 428.512f, 327.488f)
                curveTo(429.781f, 327.871f, 431.039f, 328.289f, 432.289f, 328.734f)
                curveTo(433.535f, 329.18f, 434.773f, 329.66f, 435.996f, 330.164f)
                curveTo(437.223f, 330.672f, 438.434f, 331.211f, 439.633f, 331.777f)
                curveTo(440.832f, 332.344f, 442.016f, 332.941f, 443.184f, 333.563f)
                curveTo(444.352f, 334.188f, 445.504f, 334.844f, 446.641f, 335.523f)
                curveTo(447.777f, 336.207f, 448.898f, 336.914f, 450.0f, 337.652f)
                curveTo(451.102f, 338.387f, 452.188f, 339.152f, 453.25f, 339.941f)
                curveTo(454.316f, 340.73f, 455.359f, 341.547f, 456.387f, 342.387f)
                curveTo(457.41f, 343.227f, 458.414f, 344.094f, 459.395f, 344.984f)
                curveTo(460.379f, 345.875f, 461.34f, 346.785f, 462.277f, 347.723f)
                curveTo(463.215f, 348.66f, 464.125f, 349.621f, 465.016f, 350.605f)
                curveTo(465.906f, 351.586f, 466.773f, 352.59f, 467.613f, 353.613f)
                curveTo(468.453f, 354.641f, 469.27f, 355.684f, 470.059f, 356.75f)
                curveTo(470.848f, 357.813f, 471.613f, 358.898f, 472.348f, 360.0f)
                curveTo(473.086f, 361.102f, 473.793f, 362.219f, 474.477f, 363.359f)
                curveTo(475.156f, 364.496f, 475.813f, 365.648f, 476.438f, 366.816f)
                curveTo(477.059f, 367.984f, 477.656f, 369.168f, 478.223f, 370.367f)
                curveTo(478.789f, 371.566f, 479.328f, 372.777f, 479.836f, 374.004f)
                curveTo(480.34f, 375.227f, 480.82f, 376.465f, 481.266f, 377.711f)
                curveTo(481.711f, 378.961f, 482.129f, 380.219f, 482.512f, 381.488f)
                curveTo(482.898f, 382.754f, 483.25f, 384.031f, 483.574f, 385.32f)
                curveTo(483.895f, 386.605f, 484.184f, 387.898f, 484.445f, 389.199f)
                curveTo(484.703f, 390.496f, 484.93f, 391.805f, 485.125f, 393.113f)
                curveTo(485.316f, 394.426f, 485.48f, 395.742f, 485.609f, 397.063f)
                curveTo(485.738f, 398.379f, 485.836f, 399.703f, 485.902f, 401.027f)
                curveTo(485.969f, 402.348f, 486.0f, 403.676f, 486.0f, 405.0f)
                close()
                moveTo(486.0f, 405.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(405.0f, 291.594f)
                curveTo(396.055f, 291.594f, 388.801f, 284.34f, 388.801f, 275.395f)
                lineTo(388.801f, 259.199f)
                curveTo(388.801f, 250.254f, 396.055f, 243.0f, 405.0f, 243.0f)
                curveTo(413.945f, 243.0f, 421.199f, 250.254f, 421.199f, 259.199f)
                lineTo(421.199f, 275.395f)
                curveTo(421.199f, 284.34f, 413.945f, 291.594f, 405.0f, 291.594f)
                close()
                moveTo(405.0f, 291.594f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(496.648f, 329.555f)
                curveTo(492.504f, 329.555f, 488.355f, 327.977f, 485.191f, 324.813f)
                curveTo(478.863f, 318.488f, 478.863f, 308.23f, 485.184f, 301.902f)
                lineTo(496.633f, 290.449f)
                curveTo(502.961f, 284.121f, 513.219f, 284.121f, 519.547f, 290.445f)
                curveTo(525.875f, 296.77f, 525.875f, 307.027f, 519.555f, 313.355f)
                lineTo(508.109f, 324.809f)
                curveTo(504.945f, 327.973f, 500.793f, 329.555f, 496.648f, 329.555f)
                close()
                moveTo(496.648f, 329.555f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(550.801f, 421.199f)
                lineTo(534.609f, 421.199f)
                curveTo(525.66f, 421.199f, 518.406f, 413.945f, 518.406f, 405.0f)
                curveTo(518.406f, 396.055f, 525.66f, 388.801f, 534.609f, 388.801f)
                lineTo(550.801f, 388.801f)
                curveTo(559.746f, 388.801f, 567.0f, 396.055f, 567.0f, 405.0f)
                curveTo(567.0f, 413.945f, 559.746f, 421.199f, 550.801f, 421.199f)
                close()
                moveTo(550.801f, 421.199f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(508.094f, 524.297f)
                curveTo(503.949f, 524.297f, 499.797f, 522.715f, 496.633f, 519.551f)
                lineTo(485.184f, 508.098f)
                curveTo(478.863f, 501.77f, 478.863f, 491.512f, 485.191f, 485.188f)
                curveTo(491.52f, 478.863f, 501.781f, 478.859f, 508.109f, 485.191f)
                lineTo(519.555f, 496.645f)
                curveTo(525.875f, 502.973f, 525.875f, 513.23f, 519.547f, 519.555f)
                curveTo(516.383f, 522.719f, 512.238f, 524.297f, 508.094f, 524.297f)
                close()
                moveTo(508.094f, 524.297f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(405.0f, 567.0f)
                curveTo(396.055f, 567.0f, 388.801f, 559.746f, 388.801f, 550.801f)
                lineTo(388.801f, 534.605f)
                curveTo(388.801f, 525.66f, 396.055f, 518.406f, 405.0f, 518.406f)
                curveTo(413.945f, 518.406f, 421.199f, 525.66f, 421.199f, 534.605f)
                lineTo(421.199f, 550.801f)
                curveTo(421.199f, 559.746f, 413.945f, 567.0f, 405.0f, 567.0f)
                close()
                moveTo(405.0f, 567.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(301.906f, 524.297f)
                curveTo(297.762f, 524.297f, 293.617f, 522.719f, 290.453f, 519.555f)
                curveTo(284.125f, 513.23f, 284.125f, 502.973f, 290.445f, 496.645f)
                lineTo(301.891f, 485.191f)
                curveTo(308.219f, 478.859f, 318.48f, 478.863f, 324.809f, 485.188f)
                curveTo(331.137f, 491.512f, 331.137f, 501.77f, 324.816f, 508.098f)
                lineTo(313.367f, 519.551f)
                curveTo(310.203f, 522.715f, 306.051f, 524.297f, 301.906f, 524.297f)
                close()
                moveTo(301.906f, 524.297f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(275.391f, 421.199f)
                lineTo(259.199f, 421.199f)
                curveTo(250.254f, 421.199f, 243.0f, 413.945f, 243.0f, 405.0f)
                curveTo(243.0f, 396.055f, 250.254f, 388.801f, 259.199f, 388.801f)
                lineTo(275.391f, 388.801f)
                curveTo(284.34f, 388.801f, 291.594f, 396.055f, 291.594f, 405.0f)
                curveTo(291.594f, 413.945f, 284.34f, 421.199f, 275.391f, 421.199f)
                close()
                moveTo(275.391f, 421.199f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(313.352f, 329.555f)
                curveTo(309.207f, 329.555f, 305.055f, 327.973f, 301.891f, 324.809f)
                lineTo(290.445f, 313.355f)
                curveTo(284.125f, 307.027f, 284.125f, 296.77f, 290.453f, 290.445f)
                curveTo(296.781f, 284.121f, 307.039f, 284.117f, 313.367f, 290.449f)
                lineTo(324.816f, 301.902f)
                curveTo(331.137f, 308.23f, 331.137f, 318.488f, 324.809f, 324.813f)
                curveTo(321.645f, 327.977f, 317.496f, 329.555f, 313.352f, 329.555f)
                close()
                moveTo(313.352f, 329.555f)
            }
        }
        .build()
        return _lightsolid!!
    }

private var _lightsolid: ImageVector? = null
