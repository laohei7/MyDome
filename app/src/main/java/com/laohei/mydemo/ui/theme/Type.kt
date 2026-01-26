package com.laohei.mydemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.laohei.mydemo.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val LatoFontFamily = FontFamily(
    Font(R.font.lato_thin, FontWeight.Thin),
    Font(R.font.lato_thinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.lato_black, FontWeight.Black),
    Font(R.font.lato_blackitalic, FontWeight.Black, FontStyle.Italic)
)

fun LatoStyle(
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 16.sp,
    isItalic: Boolean = false, // 增加一个布尔切换
    color: Color = Color.Unspecified
): TextStyle = TextStyle(
    fontFamily = LatoFontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal,
    color = color
)