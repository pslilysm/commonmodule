package pers.cxd.corelibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A Base Fragment extends Fragment which implements UiComponent, FragmentFinder.
 * Support to find fragment when our fragment has been low-memory-killed.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public abstract class BaseFmt extends Fragment implements UiComponent, FragmentFinder {

    protected final String TAG = "DEBUG_" + this.getClass().getSimpleName();

    protected ViewGroup mContentView;

    protected  <V extends View> V findViewById(int id){
        return mContentView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null){
            int layoutId = getLayoutId();
            if (layoutId != 0) {
                mContentView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
            }
            setUp(savedInstanceState);
        }
        return mContentView;
    }

    @Nullable
    @Override
    public <T extends Fragment> T findFragment(Class<? extends Fragment> fmtClass, Object... args) {
        return UiComponentPlugins.sSimpleFinder.findFragment(fmtClass, args);
    }

    @Override
    public void registerActivityResultCallback(OnActivityResultCallback callback) {
        UiComponentPlugins.registerActivityResultCallback(this, callback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UiComponentPlugins.getActivityResultCallbacks(this).forEach(callback -> callback.onActivityResult(requestCode, resultCode, data));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UiComponentPlugins.removeActivityResultCallbacks(this);
        performOnDestroy();
    }

}
