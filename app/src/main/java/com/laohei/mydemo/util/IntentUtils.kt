package com.laohei.mydemo.util

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log

private const val TAG = "IntentUtils"

fun Context.startSplitAtomic(pkg1: String, pkg2: String) {
    val intent1 = packageManager.getLaunchIntentForPackage(pkg1) ?: return
    val intent2 = packageManager.getLaunchIntentForPackage(pkg2) ?: return

    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    intent2.apply {
        addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    }

    startActivities(arrayOf(intent1, intent2))
}

fun Context.startSplitScreen(packageName1: String, packageName2: String) {
    try {
        val pm = this.packageManager
        val intent1 = pm.getLaunchIntentForPackage(packageName1)
        val intent2 = pm.getLaunchIntentForPackage(packageName2)

        if (intent1 != null && intent2 != null) {
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent1)

            Handler(Looper.getMainLooper()).postDelayed({
                val options = ActivityOptions.makeBasic()
                try {
                    // 通过反射或 API 尝试设置启动到相邻窗口
                    // 在部分 Android 版本中，设置这个 Flag 比 Intent Flag 更稳
                    val method = options.javaClass.getMethod("setLaunchWindowingMode", Int::class.java)
                    method.invoke(options, 3) // 3 代表 WINDOWING_MODE_SPLIT_SCREEN_PRIMARY/SECONDARY
                } catch (e: Exception) {
                    // 如果反射失败，依然回退到基础模式
                }

                intent2.apply {
                    addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    // 关键点：加上这个标志可以防止意图被现有的 Task 吞掉
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }

                startActivity(intent2, options.toBundle())
//                intent2.apply {
//                    addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
//                }
//                this.startActivity(intent2)
                Log.d(TAG, "startSplitScreen: 分屏成功")
            }, 50)
        }
    } catch (e: Exception) {
        Log.d(TAG, "startSplitScreen: 分屏失败 ${e.message}")
    }
}

fun Context.startSplitScreenSync(packageName1: String, packageName2: String) {
    try {
        val pm = this.packageManager
        val intent1 = pm.getLaunchIntentForPackage(packageName1)
        val intent2 = pm.getLaunchIntentForPackage(packageName2)

        if (intent1 != null && intent2 != null) {
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent1)

//            intent2.apply {
//                addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
//            }
//            this.startActivity(intent2)
            val options = ActivityOptions.makeBasic()
            try {
                // 通过反射或 API 尝试设置启动到相邻窗口
                // 在部分 Android 版本中，设置这个 Flag 比 Intent Flag 更稳
                val method = options.javaClass.getMethod("setLaunchWindowingMode", Int::class.java)
                method.invoke(options, 3) // 3 代表 WINDOWING_MODE_SPLIT_SCREEN_PRIMARY/SECONDARY
            } catch (e: Exception) {
                // 如果反射失败，依然回退到基础模式
            }

            intent2.apply {
                addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                // 关键点：加上这个标志可以防止意图被现有的 Task 吞掉
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }

            startActivity(intent2, options.toBundle())
            Log.d(TAG, "startSplitScreen: 分屏成功")
        } else {
            Log.d(TAG, "startSplitScreen: 目标应用暂不支持分屏")
        }
    } catch (e: Exception) {
        Log.d(TAG, "startSplitScreen: 分屏失败 ${e.message}")
    }
}