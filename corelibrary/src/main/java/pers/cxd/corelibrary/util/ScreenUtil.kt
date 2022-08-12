package pers.cxd.corelibrary.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import pers.cxd.corelibrary.AppHolder

/**
 * Miscellaneous screen utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object ScreenUtil {
    private val sApplication = AppHolder.get()
    val isDarkMode: Boolean
        get() = (sApplication!!.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                == Configuration.UI_MODE_NIGHT_YES)
    val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId =
                sApplication!!.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = sApplication.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    @kotlin.jvm.JvmStatic
    val width: Int
        get() {
            val wm = sApplication!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }
    val height: Int
        get() {
            val wm = sApplication!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }
    val realWidth: Int
        get() {
            val wm = sApplication!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getRealMetrics(dm)
            return dm.widthPixels
        }
    val realHeight: Int
        get() {
            val wm = sApplication!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            wm.defaultDisplay.getRealMetrics(dm)
            return dm.heightPixels
        }

    @kotlin.jvm.JvmStatic
    fun dip2px(dp: Float): Int {
        val scale = sApplication!!.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun pxToDip(px: Float): Int {
        val scale = sApplication!!.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    fun spToPx(sp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            sApplication!!.resources.displayMetrics
        ).toInt()
    }

    val navBarHeight: Int
        get() {
            val resources = sApplication!!.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceId > 0) {
                resources.getDimensionPixelSize(resourceId)
            } else 0
        }
    val screenDensityDpi: Int
        get() = sApplication!!.resources.displayMetrics.densityDpi

    fun setSystemUiFlag(
        window: Window,
        layoutFullScreen: Boolean,
        fullScreen: Boolean,
        lightMode: Boolean,
        hideNav: Boolean
    ) {
        var flag = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        if (layoutFullScreen) {
            flag = flag or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (fullScreen) {
            flag = flag or (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
        if (hideNav) flag = flag or (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        if (lightMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flag = flag or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.decorView.systemUiVisibility = flag
        window.statusBarColor = Color.TRANSPARENT
    }

    fun keepScreenOn(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}