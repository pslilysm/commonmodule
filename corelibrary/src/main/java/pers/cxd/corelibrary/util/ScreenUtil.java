package pers.cxd.corelibrary.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import pers.cxd.corelibrary.AppHolder;

/**
 * Miscellaneous screen utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class ScreenUtil {

    private static final Application sApplication = AppHolder.get();

    public static boolean isDarkMode(){
        return (sApplication.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = sApplication.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = sApplication.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getWidth() {
        WindowManager wm = (WindowManager) sApplication.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getHeight() {
        WindowManager wm = (WindowManager) sApplication.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getRealWidth() {
        WindowManager wm = (WindowManager) sApplication.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);
        return dm.widthPixels;
    }

    public static int getRealHeight() {
        WindowManager wm = (WindowManager) sApplication.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);
        return dm.heightPixels;
    }

    public static int dip2px(float dp){
        final float scale = sApplication.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int pxToDip(float px){
        final float scale = sApplication.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, sApplication.getResources().getDisplayMetrics());
    }

    public static int getNavBarHeight(){
        Resources resources = sApplication.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getScreenDensityDpi() {
        return sApplication.getResources().getDisplayMetrics().densityDpi;
    }

    public static void setSystemUiFlag(Window window, boolean layoutFullScreen, boolean fullScreen,  boolean lightMode, boolean hideNav){
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (layoutFullScreen){
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }
        if (fullScreen){
            flag |= View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        if (hideNav) flag |=  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (lightMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        window.getDecorView().setSystemUiVisibility(flag);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    public static void keepScreenOn(Window window){
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
