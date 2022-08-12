package pers.cxd.corelibrary.simple

import android.animation.Animator

/**
 * make all implementation of [Animator.AnimatorListener] be default, the purpose is to simplify code
 * when some callback don't need to be used
 *
 * @author pslilysm
 * @since 1.1.4
 */
interface SimpleAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
}