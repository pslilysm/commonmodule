package pers.cxd.corelibrary.simple;

import android.view.animation.Animation;

/**
 * make all implementation of {@link Animation.AnimationListener} be default, the purpose is to simplify code
 * when some callback don't need to be used
 *
 * @author pslilysm
 * @since 1.0.0
 */
public interface SimpleAnimationListener extends Animation.AnimationListener {

    @Override
    default void onAnimationStart(Animation animation) {

    }

    @Override
    default void onAnimationEnd(Animation animation) {

    }

    @Override
    default void onAnimationRepeat(Animation animation) {

    }
}
