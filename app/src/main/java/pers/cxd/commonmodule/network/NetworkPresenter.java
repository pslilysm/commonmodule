package pers.cxd.commonmodule.network;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import pers.cxd.mvplibrary.MvpModel;
import pers.cxd.mvplibrary.MvpPresenter;
import pers.cxd.mvplibrary.MvpView;
import pers.cxd.networklibrary.HttpCallback;

public class NetworkPresenter<V extends MvpView, M extends MvpModel> extends MvpPresenter<V, M> {

    protected CompositeDisposable mSubscription;

    @Override
    protected void attach(V v, M m) {
        super.attach(v, m);
        mSubscription = new CompositeDisposable();
    }

    protected abstract class BaseHttpCallbackImpl<D> implements HttpCallback<D> {

        @Override
        public void addDisposable(Disposable disposable) {
            if (mSubscription != null) {
                mSubscription.add(disposable);
            }
        }

    }

    @Override
    protected void detach() {
        super.detach();
        if (mSubscription != null){
            mSubscription.clear();
        }
        mSubscription = null;
    }
}
