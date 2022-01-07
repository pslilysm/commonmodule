package pers.cxd.corelibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * A Base Fragment extends Fragment which implements UiComponent, FragmentFinder.
 * Support to find fragment when our fragment has been low-memory-killed.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public abstract class BaseFmt extends Fragment implements UiComponent, FragmentFinder {

    protected final String TAG = "DEBUG_CXD_" + this.getClass().getSimpleName();

    protected ViewGroup mContentView;

    protected  <V extends View> V findViewById(int id){
        return mContentView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null){
            mContentView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
            setUp(savedInstanceState);
        }
        return mContentView;
    }

    @Nullable
    @Override
    public <T extends Fragment> T findFragment(FragmentManager manager, Class<T> clazz, int position) {
        return UiComponentPlugins.sSimpleFinder.findFragment(manager, clazz, position);
    }

    @Override
    public <T extends Fragment> T findOrCreateFmt(Class<T> clazz, int position) {
        return UiComponentPlugins.findOrCreateFmt(getChildFragmentManager(), clazz, position, this);
    }

}
