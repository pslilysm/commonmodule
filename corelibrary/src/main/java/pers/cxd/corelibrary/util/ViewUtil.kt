package pers.cxd.corelibrary.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup.MarginLayoutParams

/**
 * Miscellaneous [View] utility methods.
 *
 * @author pslilysm
 * @since 1.1.6
 */
object ViewUtil {
    private val sFinishListener =
        View.OnClickListener { v: View -> (v.context as Activity).finish() }

    /**
     * Set the top margin to the same height as the status bar
     *
     * @param v the margin you wanna to set with
     */
    @kotlin.jvm.JvmStatic
    fun View.setStatusBarMargin() {
        val lp = this.layoutParams as MarginLayoutParams
        lp.topMargin += ScreenUtil.statusBarHeight
        this.layoutParams = lp
    }

    /**
     * Set the top padding to the same height as the status bar
     *
     * @param v the padding you wanna to set with
     */
    @kotlin.jvm.JvmStatic
    fun View.setStatusBarPadding() {
        this.setPadding(
            this.paddingLeft,
            this.paddingTop + ScreenUtil.statusBarHeight,
            this.paddingRight,
            this.paddingBottom
        )
    }

    /**
     * Finish activity while view clicked
     *
     * @param v the listener you wanna to set with
     */
    @kotlin.jvm.JvmStatic
    fun View.setDoFinishOnClickListener() {
        this.setOnClickListener(sFinishListener)
    }
}