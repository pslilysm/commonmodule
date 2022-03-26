package pers.cxd.corelibrary;

import android.app.Application;

import pers.cxd.corelibrary.util.reflection.ReflectionUtil;

/**
 * Application instance's holder
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class AppHolder {

    private static volatile Application sApplication;

    public static void set(Application sApplication) {
        AppHolder.sApplication = sApplication;
    }

    public static Application get() {
        if (sApplication == null) {
            synchronized (AppHolder.class) {
                if (sApplication == null) {
                    try {
                        Object activityThread = ReflectionUtil.getStaticFieldValue("android.app.ActivityThread", "sCurrentActivityThread");
                        sApplication = ReflectionUtil.getFieldValue(activityThread, "mInitialApplication");
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException("Unable to get application by reflection, " +
                                "maybe the mInitialApplication or sCurrentActivityThread field in ActivityThread is denied to access by android system, " +
                                "you can check your phone log for more detail and call set() manually", e);
                    }
                }
            }
        }
        return sApplication;
    }

}
