package pers.cxd.networklibrary;

import io.reactivex.rxjava3.disposables.Disposable;

public abstract class SimpleHttpCallback<D> implements HttpCallback<D> {

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public boolean handleAnotherError(Throwable e) {
        return true;
    }

    @Override
    public void onComplete() {

    }

}
