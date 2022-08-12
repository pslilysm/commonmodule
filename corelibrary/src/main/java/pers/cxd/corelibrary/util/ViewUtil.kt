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
    fun setStatusBarMargin(v: View) {
        val lp = v.layoutParams as MarginLayoutParams
        lp.topMargin += ScreenUtil.statusBarHeight
        v.layoutParams = lp
    }

    /**
     * Set the top padding to the same height as the status bar
     *
     * @param v the padding you wanna to set with
     */
    fun setStatusBarPadding(v: View) {
        v.setPadding(
            v.paddingLeft,
            v.paddingTop + ScreenUtil.statusBarHeight,
            v.paddingRight,
            v.paddingBottom
        )
    }

    /**
     * Finish activity while view clicked
     *
     * @param v the listener you wanna to set with
     */
    fun setDoFinishOnClickListener(v: View) {
        v.setOnClickListener(sFinishListener)
    }
}