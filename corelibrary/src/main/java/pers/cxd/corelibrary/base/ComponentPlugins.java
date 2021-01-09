package pers.cxd.corelibrary.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pers.cxd.corelibrary.util.reflection.ReflectionUtil;

public class ComponentPlugins {

    protected static final FragmentFinder sSimpleFinder = new FragmentFinder() {
        @Override
        public <T extends Fragment> T findFragment(FragmentManager manager, Class<T> clazz, int position) {
            return (T) manager.findFragmentByTag(clazz.getName());
        }
    };

    protected static <T extends Fragment> T findOrCreateFmt(FragmentManager manager, Class<T> clazz, int position, FragmentFinder finder){
        T fmt = finder.findFragment(manager, clazz, position);
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
