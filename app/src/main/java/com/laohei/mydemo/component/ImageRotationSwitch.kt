package com.laohei.mydemo.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.laohei.mydemo.R

@Preview(showBackground = true)
@Composable
fun ImageRotationSwitch() {
    // 控制翻转状态
    var rotated by remember { mutableStateOf(false) }

    // 平滑动画处理角度
    val rotation by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "RotationAnimation"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer {
                    // 应用 Y 轴旋转
                    rotationY = rotation
                    cameraDistance = 12f * density // 增加相机距离以获得更好的 3D 透视感
                }
                .clickable { rotated = !rotated },
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                // 正面图片
                Image(
                    painter = painterResource(id = R.drawable.image_front),
                    contentDescription = "Front",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // 背面图片
                Image(
                    painter = painterResource(id = R.drawable.image_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            // 关键点：将背面图片旋转 180 度，否则它是镜像反转的
                            rotationY = 180f
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}