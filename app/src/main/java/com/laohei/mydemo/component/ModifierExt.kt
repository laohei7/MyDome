package com.laohei.mydemo.component

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.allAroundShadow(
    color: Color = Color.Black.copy(alpha = 0.2f),
    radius: Dp = 10.dp,
    shape: Shape = RectangleShape,
    spread: Dp = 0.dp
) = this.drawBehind {
    val shadowRadius = radius.toPx()
    val spreadRadius = spread.toPx()

    // 绘制阴影层
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        if (shadowRadius > 0f) {
            // 关键：设置模糊滤镜实现全向扩散
            frameworkPaint.maskFilter = BlurMaskFilter(shadowRadius, BlurMaskFilter.Blur.NORMAL)
        }
        frameworkPaint.color = color.toArgb()

        val size = size
        val left = -spreadRadius
        val top = -spreadRadius
        val right = size.width + spreadRadius
        val bottom = size.height + spreadRadius

        // 这里假设是矩形，如果是圆角矩形需用 drawRoundRect
        canvas.drawRect(left, top, right, bottom, paint)
    }
}