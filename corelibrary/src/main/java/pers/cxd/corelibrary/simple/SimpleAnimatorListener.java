package pers.cxd.corelibrary.simple;

import android.animation.Animator;

/**
 * make all implementation of {@link Animator.AnimatorListener} be default, the purpose is to simplify code
 * when some callback don't need to be used
 *
 * @author pslilysm
 * @since 1.1.4
 */
public interface SimpleAnimatorListener extends Animator.AnimatorListener {

    @Override
    default void onAnimationStart(Animator animation) {

    }

    @Override
    default void onAnimationEnd(Animator animation) {

    }

    @Override
    default void onAnimationCancel(Animator animation) {

    }

    @Override
    default void onAnimationRepeat(Animator animation) {

    }

}
