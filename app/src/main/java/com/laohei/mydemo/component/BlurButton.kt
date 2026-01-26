package com.laohei.mydemo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.laohei.mydemo.util.px

@Composable
fun BlurButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    background: Color = Color.Transparent,
    shape: Shape = ButtonDefaults.shape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    blur: Dp = 10.px(),
    content: @Composable (RowScope.() -> Unit)
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .clip(shape)
                .blur(blur, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(background, shape)
        )

        Button(
            enabled = enabled,
            modifier = Modifier.matchParentSize(),
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            onClick = onClick,
            shape = shape,
            contentPadding = contentPadding
        ) {
            content()
        }
    }
}