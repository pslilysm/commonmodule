package pers.cxd.corelibrary.simple

import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener

/**
 * make all implementation of [Animation.AnimationListener] be default, the purpose is to simplify code
 * when some callback don't need to be used
 *
 * @author pslilysm
 * @since 1.0.0
 */
interface SimpleAnimationListener : AnimationListener {
    override fun onAnimationStart(animation: Animation) {}
    override fun onAnimationEnd(animation: Animation) {}
    override fun onAnimationRepeat(animation: Animation) {}
}