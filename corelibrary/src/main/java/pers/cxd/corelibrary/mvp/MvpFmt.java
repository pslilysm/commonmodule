package pers.cxd.corelibrary.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pers.cxd.corelibrary.base.BaseFmt;

/**
 * The MvpFmt is designed by Model-View-Presenter architecture
 *
 * @author pslilysm
 * @since 1.0.0
 * @param <P> the presenter is responsible for the interaction between the view and the model
 * @param <M> the model provide data source
 */
public abstract class MvpFmt<P extends Presenter, M> extends BaseFmt {

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
