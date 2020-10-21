package pers.cxd.mvplibrary;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import pers.cxd.networklibrary.HttpCallback;

public class MvpPresenter<V extends MvpView, M extends MvpModel> {

    protected V mView;
    protected M mModel;

    CompositeDisposable mSubscription;

    protected void attach(V v, M m){
        mView = v;
        mModel = m;
        mSubscription = new CompositeDisposable();
    }

    protected abstract class MvpHttpCallback<D> implements HttpCallback<D>{

        @Override
        public void addDisposable(Disposable disposable) {
            if (mSubscription != null){
                mSubscription.add(disposable);
            }
        }

        @Override
        public boolean handleAnotherError(Throwable e) {
            return true;
        }

        @Override
        public void onComplete() {}

    }

    protected void detach(){
        mView = null;
        mModel = null;
        if (mSubscription != null){
            mSubscription.clear();
        }
        mSubscription = null;
    }

}
