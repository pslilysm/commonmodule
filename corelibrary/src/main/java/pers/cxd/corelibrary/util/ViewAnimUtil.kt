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
    fun showViewWithAlpha(v: View) {
        if (v.visibility != View.VISIBLE) {
            val alphaAnim = AlphaAnimation(0f, 1f)
            alphaAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            alphaAnim.fillAfter = true
            v.startAnimation(alphaAnim)
        }
    }

    fun hideViewWithAlpha(v: View) {
        if (v.visibility == View.VISIBLE) {
            val alphaAnim = AlphaAnimation(1f, 0f)
            alphaAnim.duration = DEFAULT_ANIM_DURATION.toLong()
            //            alphaAnim.setFillAfter(true);
            v.startAnimation(alphaAnim)
        }
    }

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

    fun translateFromLeftToRight(v: View) {
        val transAnim = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
        )
        transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
        transAnim.fillAfter = true
        v.startAnimation(transAnim)
    }

    fun translateFromRightToLeft(v: View) {
        val transAnim = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
        )
        transAnim.duration = DEFAULT_ANIM_DURATION.toLong()
        transAnim.fillAfter = true
        v.startAnimation(transAnim)
    }

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