package com.laohei.mydemo.floating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.laohei.mydemo.pixel_design.background2

@Composable
fun OverlayGuideScreen(
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background2.copy(0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            onClose()
        }) {
            Text(text = "关闭")
        }
    }
}
