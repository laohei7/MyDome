package com.laohei.mydemo.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.laohei.mydemo.floating.OverlayGuideScreen

class FloatingService : Service() {
    private lateinit var windowManager: WindowManager
    private var composeView: ComposeView? = null
    private val lifecycleOwner = FloatingWindowLifecycleOwner()
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (composeView == null) {
            showFloatingWindow()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        composeView?.let {
            if (it.parent != null) windowManager.removeView(it)
        }
    }

    private fun showFloatingWindow() {
        windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams().apply {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            }

            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT

            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or  // 允许在屏幕范围内布局
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS    // 延伸至屏幕外（突破状态栏限制）

            format = PixelFormat.TRANSLUCENT
        }

        composeView = ComposeView(this).apply {
            val lifecycleOwner = FloatingWindowLifecycleOwner()
            lifecycleOwner.performRestore(null)
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

            setViewTreeLifecycleOwner(lifecycleOwner)
            setViewTreeSavedStateRegistryOwner(lifecycleOwner)

            setContent {
                OverlayGuideScreen(
                    onClose = {
                        stopSelf()
                    }
                )
            }
        }

        // 4. 添加到窗口
        windowManager.addView(composeView, params)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

}


private class FloatingWindowLifecycleOwner : LifecycleOwner, SavedStateRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle = lifecycleRegistry
    override val savedStateRegistry: SavedStateRegistry =
        savedStateRegistryController.savedStateRegistry

    fun handleLifecycleEvent(event: Lifecycle.Event) = lifecycleRegistry.handleLifecycleEvent(event)
    fun performRestore(savedState: Bundle?) =
        savedStateRegistryController.performRestore(savedState)
}