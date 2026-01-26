package com.laohei.mydemo.pixel_design

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val background1 = Color(0xFF21242D)
val background2 = Color(0xFF16181E)

val buttonFocusColor = Color(0xFF00B9AE)
val buttonColor = Color(0xFFF9F9F9)
val coverColor = Color(0xFF16181E)

@Composable
fun searchTextFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = background1,
    focusedContainerColor = background1,
    unfocusedBorderColor = background1,
    focusedBorderColor = background1
)

@Composable
fun watchButtonColors() = ButtonDefaults.buttonColors(
    containerColor = buttonFocusColor
)

@Composable
fun watchlistButtonColors() = ButtonDefaults.buttonColors(
    containerColor = buttonColor.copy(0.2f)
)

val imageCoverBrush = Brush.verticalGradient(
    colors = listOf(
        coverColor.copy(0f),
        coverColor.copy(.4f)
    )
)