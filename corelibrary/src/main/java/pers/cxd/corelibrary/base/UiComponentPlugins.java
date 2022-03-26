package pers.cxd.corelibrary.base;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pers.cxd.corelibrary.util.ExceptionUtil;
import pers.cxd.corelibrary.util.ThreadUtil;
import pers.cxd.corelibrary.util.reflection.ReflectionUtil;

/**
 * Provide some default UiComponent interface implementation
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class UiComponentPlugins {

    public static final FragmentFinder sSimpleFinder = new FragmentFinder() {
        @Nullable
        @Override
        public <T extends Fragment> T findFragment(Class<? extends Fragment> fmtClass, Object... args) {
            FragmentManager manager = (FragmentManager) args[0];
            return (T) manager.findFragmentByTag(fmtClass.getName());
        }
    };
    public static final FragmentFinder sViewPager2Finder = new FragmentFinder() {
        @Nullable
        @Override
        public <T extends Fragment> T findFragment(Class<? extends Fragment> fmtClass, Object... args) {
            FragmentManager manager = (FragmentManager) args[0];
            int position = (int) args[1];
            return (T) manager.findFragmentByTag("f" + position);
        }
    };
    private static final String TAG = "DEBUG_" + UiComponentPlugins.class.getSimpleName();
    private static final Map<UiComponent, List<OnActivityResultCallback>> sComponentCallbackMap = new ArrayMap<>();

    public static <T extends Fragment> T findOrCreateFmt(Class<T> fmtClass, FragmentFinder finder, Object... args) {
        return findOrCreateFmt(fmtClass, finder, fmtClazz -> {
            try {
                return ReflectionUtil.newInstance(fmtClazz);
            } catch (ReflectiveOperationException e) {
                ExceptionUtil.rethrow(e);
            }
            return null;
        }, args);
    }

    public static <T extends Fragment> T findOrCreateFmt(Class<T> fmtClass, FragmentFinder finder, FragmentCreator<T> creator, Object... args) {
        T fmt = finder.findFragment(fmtClass, args);
        Log.i(TAG, "findOrCreateFmt: " + fmt);
        if (fmt == null) {
            fmt = creator.create(fmtClass);
        }
        return fmt;
    }

    public static void registerActivityResultCallback(UiComponent component, OnActivityResultCallback callback) {
        ThreadUtil.checkIsMainThread();
        sComponentCallbackMap.computeIfAbsent(component, o -> new ArrayList<>()).add(callback);
    }

    public static List<OnActivityResultCallback> getActivityResultCallbacks(UiComponent component) {
        ThreadUtil.checkIsMainThread();
        return sComponentCallbackMap.computeIfAbsent(component, o -> new ArrayList<>());
    }

    public static void unregisterActivityResultCallbacks(UiComponent component) {
        ThreadUtil.checkIsMainThread();
        sComponentCallbackMap.remove(component);
    }

}
