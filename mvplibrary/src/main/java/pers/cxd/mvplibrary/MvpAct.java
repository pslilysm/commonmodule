package pers.cxd.mvplibrary;

import android.os.Bundle;

import androidx.annotation.Nullable;

import pers.cxd.baselibrary.BaseAct;

public abstract class MvpAct<P extends MvpPresenter, M extends MvpModel> extends BaseAct
        implements MvpView {

    protected abstract P createPresenter();
    protected abstract M createModel();

    protected P mPresenter;
    protected M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null){
            mModel = createModel();
            mPresenter.attach(this, mModel);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.detach();
            mPresenter = null;
        }
    }
}
