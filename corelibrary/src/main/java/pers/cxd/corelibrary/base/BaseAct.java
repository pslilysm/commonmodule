package pers.cxd.corelibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pers.cxd.corelibrary.Log;

public abstract class BaseAct extends AppCompatActivity implements Component, FragmentFinder {

    protected final String TAG = Log.TAG + this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUp(savedInstanceState);
    }

    @Nullable
    @Override
    public <T extends Fragment> T findFragment(FragmentManager manager, Class<T> clazz, int position) {
        return ComponentPlugins.sSimpleFinder.findFragment(manager, clazz, position);
    }

    @Override
    public <T extends Fragment> T findOrCreateFmt(Class<T> clazz, int position) {
        return ComponentPlugins.findOrCreateFmt(getSupportFragmentManager(), clazz, position, this);
    }

}
