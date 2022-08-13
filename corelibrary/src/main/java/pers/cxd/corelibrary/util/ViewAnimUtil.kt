package pers.cxd.corelibrary.util

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

/**
 * Miscellaneous [Animation] utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object ViewAnimUtil {
    private const val DEFAULT_ANIM_DURATION = 250

    @kotlin.jvm.JvmStatic
    fun showViewWithAlpha(v: View) {
        if (v.visibility != View.VISIBLE) {
            val alphaAnim = AlphaAnimation(0f, 1f)
            alphaAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            alphaAnim.fillAfter = true
            v.startAnimation(alphaAnim)
        }
    }

    @kotlin.jvm.JvmStatic
    fun hideViewWithAlpha(v: View) {
        if (v.visibility == View.VISIBLE) {
            val alphaAnim = AlphaAnimation(1f, 0f)
            alphaAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            //            alphaAnim.setFillAfter(true);
            v.startAnimation(alphaAnim)
        }
    }

    @kotlin.jvm.JvmStatic
    fun showViewWithTranslateFromBottomToTop(v: View) {
        if (v.visibility != View.VISIBLE) {
            val transAnim = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
            )
            transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            transAnim.fillAfter = true
            v.startAnimation(transAnim)
        }
    }

    @kotlin.jvm.JvmStatic
    fun hideViewWithTranslateFromTopToBottom(v: View) {
        if (v.visibility == View.VISIBLE) {
            val transAnim = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
            )
            transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            v.startAnimation(transAnim)
        }
    }

    @kotlin.jvm.JvmStatic
    fun translateFromLeftToRight(v: View) {
        val transAnim = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
        )
        transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
        transAnim.fillAfter = true
        v.startAnimation(transAnim)
    }

    @kotlin.jvm.JvmStatic
    fun translateFromRightToLeft(v: View) {
        val transAnim = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
        )
        transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
        transAnim.fillAfter = true
        v.startAnimation(transAnim)
    }

    @kotlin.jvm.JvmStatic
    fun hideViewWithTranslateFromRightToLeft(v: View) {
        if (v.visibility == View.VISIBLE) {
            val transAnim = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            )
            transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            v.startAnimation(transAnim)
        }
    }

    @kotlin.jvm.JvmStatic
    fun showViewWithTranslateFromRightToLeft(v: View) {
        if (v.visibility != View.VISIBLE) {
            val transAnim = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            )
            transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            v.startAnimation(transAnim)
        }
    }

    @kotlin.jvm.JvmStatic
    fun hideViewWithTranslateFromLeftToRight(v: View) {
        if (v.visibility == View.VISIBLE) {
            val transAnim = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            )
            transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            v.startAnimation(transAnim)
        }
    }

    @kotlin.jvm.JvmStatic
    fun showViewWithTranslateFromLeftToRight(v: View) {
        if (v.visibility != View.VISIBLE) {
            val transAnim = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            )
            transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            v.startAnimation(transAnim)
        }
    }
}