package pers.cxd.baselibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFmt extends Fragment {

    protected abstract int getLayoutId();
    protected abstract void setUp();

    protected ViewGroup mContentView;

    public <V extends View> V findViewVyId(int id){
        return mContentView.findViewById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null){
            mContentView = (ViewGroup) inflater.inflate(getLayoutId(), container, false);
            setUp();
        }
        return mContentView;
    }

}
