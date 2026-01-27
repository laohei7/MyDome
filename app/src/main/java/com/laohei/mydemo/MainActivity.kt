package com.laohei.mydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.laohei.mydemo.nested_pager.TestNestedPager
import com.laohei.mydemo.pixel_design.DesignScreen
import com.laohei.mydemo.ui.theme.MyDemoTheme
import com.laohei.mydemo.util.checkOverlayPermission
import com.laohei.mydemo.util.hideSystemUI
import com.laohei.mydemo.util.requestOverlayPermission

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        if (!checkOverlayPermission()) {
            requestOverlayPermission()
        }
        setContent {
            MyDemoTheme {
//                DesignScreen()
                TestNestedPager()
            }
        }
    }
}
