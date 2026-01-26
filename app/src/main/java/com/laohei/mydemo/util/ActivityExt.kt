package com.laohei.mydemo.util

import android.app.Activity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * 设置系统状态栏和导航栏图标颜色模式。
 * [isLightMode] 为 true 时，图标显示为深色（适用于浅色背景）。
 * [isLightMode] 为 false 时，图标显示为浅色（适用于深色背景）。
 */
fun Activity.setSystemBarsDarkIcons(isLightMode: Boolean = true) {
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = isLightMode
        isAppearanceLightNavigationBars = isLightMode
    }
}

fun Activity.hideSystemUI() {
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val controller = WindowCompat.getInsetsController(window, window.decorView)

    controller.let {
        it.hide(WindowInsetsCompat.Type.systemBars())
        it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun Activity.showSystemUI() {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.show(WindowInsetsCompat.Type.systemBars())
}