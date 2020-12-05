package pers.cxd.corelibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pers.cxd.corelibrary.Log;

public abstract class BaseFmt extends Fragment implements Component {

    protected final String TAG = Log.TAG + this.getClass().getSimpleName();

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

}
