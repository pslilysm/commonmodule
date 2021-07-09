package pers.cxd.corelibrary.simple;

import android.view.animation.Animation;

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
