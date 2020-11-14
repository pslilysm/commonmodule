package pers.cxd.mvplibrary;

import android.os.Bundle;

import androidx.annotation.Nullable;

import pers.cxd.corelibrary.base.BaseAct;

public abstract class MvpAct<P extends Presenter, M> extends BaseAct {

    protected abstract P createPresenter();
    protected abstract M createModel();

    protected P mPresenter;
    protected M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mModel = createModel();
        if (mPresenter != null){
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
