package pers.cxd.corelibrary.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;


/**
 * Miscellaneous {@link View} utility methods.
 *
 * @author pslilysm
 * @since 1.1.6
 */
public class ViewUtil {

    private static final View.OnClickListener sFinishListener = v -> ((Activity) v.getContext()).finish();

    /**
     * Set the top margin to the same height as the status bar
     *
     * @param v the margin you wanna to set with
     */
    public static void setStatusBarMargin(View v) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        lp.topMargin += ScreenUtil.getStatusBarHeight();
        v.setLayoutParams(lp);
    }

    /**
     * Set the top padding to the same height as the status bar
     *
     * @param v the padding you wanna to set with
     */
    public static void setStatusBarPadding(View v) {
        v.setPadding(v.getPaddingLeft(), v.getPaddingTop() + ScreenUtil.getStatusBarHeight(), v.getPaddingRight(), v.getPaddingBottom());
    }

    /**
     * Finish activity while view clicked
     *
     * @param v the listener you wanna to set with
     */
    public static void setDoFinishOnClickListener(View v) {
        v.setOnClickListener(sFinishListener);
    }

}
