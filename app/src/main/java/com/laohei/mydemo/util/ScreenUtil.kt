package com.laohei.mydemo.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
private fun screenWidthPx() = LocalResources.current.displayMetrics.widthPixels

@Composable
private fun screenWidthDp() = LocalConfiguration.current.screenWidthDp

@Composable
private fun density() = LocalDensity.current.density

private const val DesignWidth = 1512f

@Composable
private fun ratioPx() = screenWidthPx() / DesignWidth

@Composable
private fun ratioDp() = screenWidthDp() / DesignWidth

@Composable
fun Float.px() = (this * ratioPx() / density()).dp

@Composable
fun Int.px() = this.toFloat().px()

@Composable
fun Float.realPx() = (this * ratioDp()).dp

@Composable
fun Int.realPx() = this.toFloat().realPx()

@Composable
fun Float.textPx() = (this * ratioPx() / density()).sp

@Composable
fun Int.textPx() = this.toFloat().textPx()