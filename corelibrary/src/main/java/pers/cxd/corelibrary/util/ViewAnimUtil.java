package pers.cxd.corelibrary.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class ViewAnimUtil {

    public static void showViewWithAlpha(View v){
        if (v.getVisibility() != View.VISIBLE){
            AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
            alphaAnim.setDuration(280);
            alphaAnim.setFillAfter(true);
            v.startAnimation(alphaAnim);
        }
    }

    public static void hideViewWithAlpha(View v){
        if (v.getVisibility() == View.VISIBLE){
            AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
            alphaAnim.setDuration(280);
            alphaAnim.setFillAfter(true);
            v.startAnimation(alphaAnim);
        }
    }

    public static void showViewWithTranslateFromBottomToTop(View v){
        if (v.getVisibility() != View.VISIBLE){
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
            transAnim.setDuration(280);
            transAnim.setFillAfter(true);
            v.startAnimation(transAnim);
        }
    }

    public static void hideViewWithTranslateFromTopToBottom(View v){
        if (v.getVisibility() == View.VISIBLE){
            TranslateAnimation transAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
            transAnim.setDuration(280);
            v.startAnimation(transAnim);
        }
    }

}
