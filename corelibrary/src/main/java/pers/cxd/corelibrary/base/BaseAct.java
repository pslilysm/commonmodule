package pers.cxd.corelibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/**
 * A Base Activity extends AppCompatActivity which implements UiComponent, FragmentFinder.
 * Support to find fragment when our activity has been low-memory-killed.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public abstract class BaseAct extends AppCompatActivity implements UiComponent, FragmentFinder {

    protected final String TAG = "DEBUG_"+ this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(getLayoutId());
        }
        setUp(savedInstanceState);
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * default implementation by {@link UiComponentPlugins#sSimpleFinder}
     */
    @Nullable
    @Override
    public <T extends Fragment> T findFragment(Class<? extends Fragment> fmtClass, Object... args) {
        return UiComponentPlugins.sSimpleFinder.findFragment(fmtClass, args);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UiComponentPlugins.getActivityResultCallbacks(this).forEach(callback -> callback.onActivityResult(requestCode, resultCode, data));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        performOnDestroy();
    }
}
