package com.laohei.mydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.laohei.mydemo.shared_nav.SharedNav
import com.laohei.mydemo.ui.theme.MyDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyDemoTheme {
                SharedNav()
            }
        }
    }
}
