package pers.cxd.mvplibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pers.cxd.baselibrary.BaseFmt;

public abstract class MvpFmt<P extends BasePresenter, M extends BaseModel> extends BaseFmt
        implements BaseView {

    protected abstract P createPresenter();
    protected abstract M createModel();

    protected P mPresenter;
    protected M mModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mPresenter == null){
            mPresenter = createPresenter();
            if (mPresenter != null){
                mModel = createModel();
                mPresenter.attach(this, mModel);
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detach();
            mPresenter = null;
        }
    }
}
