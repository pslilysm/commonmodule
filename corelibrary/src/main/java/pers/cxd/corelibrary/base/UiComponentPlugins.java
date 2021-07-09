package pers.cxd.corelibrary.base;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pers.cxd.corelibrary.util.reflection.ReflectionUtil;

public class UiComponentPlugins {

    public static final FragmentFinder sSimpleFinder = new FragmentFinder() {
        @Nullable
        @Override
        public <T extends Fragment> T findFragment(FragmentManager manager, Class<T> clazz, int position) {
            return (T) manager.findFragmentByTag(clazz.getName());
        }
    };

    public static final FragmentFinder sViewPager2Finder = new FragmentFinder() {
        @Nullable
        @Override
        public <T extends Fragment> T findFragment(FragmentManager manager, Class<T> clazz, int position) {
            return (T) manager.findFragmentByTag("f" + position);
        }
    };

    private static final String TAG = "DEBUG_CXD_UiComponentPlugins";

    @SuppressLint("LongLogTag")
    protected static <T extends Fragment> T findOrCreateFmt(FragmentManager manager, Class<T> clazz, int position, FragmentFinder finder){
        Log.d(TAG, "findOrCreateFmt() called with: manager = [" + manager + "], clazz = [" + clazz + "], position = [" + position + "], finder = [" + finder + "]");
        T fmt = finder.findFragment(manager, clazz, position);
        Log.i(TAG, "findOrCreateFmt: " + fmt);
        if (fmt == null){
            try {
                return ReflectionUtil.newInstance(clazz);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        return fmt;
    }

}
