package pers.cxd.corelibrary.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Miscellaneous {@link Animation} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class ViewAnimUtil {

    private static final int DEFAULT_ANIM_DURATION = 250;

    public static void showViewWithAlpha(View v) {
        if (v.getVisibility() != View.VISIBLE) {
            AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
            alphaAnim.setDuration(DEFAULT_ANIM_DURATION);
            alphaAnim.setFillAfter(true);
            v.startAnimation(alphaAnim);
        }
    }

    public static void hideViewWithAlpha(View v) {
        if (v.getVisibility() == View.VISIBLE) {
            AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
            alphaAnim.setDuration(DEFAULT_ANIM_DURATION);
//            alphaAnim.setFillAfter(true);
            v.startAnimation(alphaAnim);
        }
    }

    public static void showViewWithTranslateFromBottomToTop(View v) {
        if (v.getVisibility() != View.VISIBLE) {
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
            transAnim.setDuration(DEFAULT_ANIM_DURATION);
            transAnim.setFillAfter(true);
            v.startAnimation(transAnim);
        }
    }

    public static void hideViewWithTranslateFromTopToBottom(View v) {
        if (v.getVisibility() == View.VISIBLE) {
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
            transAnim.setDuration(DEFAULT_ANIM_DURATION);
            v.startAnimation(transAnim);
        }
    }

    public static void translateFromLeftToRight(View v) {
        TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        transAnim.setDuration(DEFAULT_ANIM_DURATION);
        transAnim.setFillAfter(true);
        v.startAnimation(transAnim);
    }

    public static void translateFromRightToLeft(View v) {
        TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        transAnim.setDuration(DEFAULT_ANIM_DURATION);
        transAnim.setFillAfter(true);
        v.startAnimation(transAnim);
    }

    public static void hideViewWithTranslateFromRightToLeft(View v) {
        if (v.getVisibility() == View.VISIBLE) {
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            transAnim.setDuration(DEFAULT_ANIM_DURATION);
            v.startAnimation(transAnim);
        }
    }

    public static void showViewWithTranslateFromRightToLeft(View v) {
        if (v.getVisibility() != View.VISIBLE) {
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            transAnim.setDuration(DEFAULT_ANIM_DURATION);
            v.startAnimation(transAnim);
        }
    }

    public static void hideViewWithTranslateFromLeftToRight(View v) {
        if (v.getVisibility() == View.VISIBLE) {
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            transAnim.setDuration(DEFAULT_ANIM_DURATION);
            v.startAnimation(transAnim);
        }
    }

    public static void showViewWithTranslateFromLeftToRight(View v) {
        if (v.getVisibility() != View.VISIBLE) {
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            transAnim.setDuration(DEFAULT_ANIM_DURATION);
            v.startAnimation(transAnim);
        }
    }

}
