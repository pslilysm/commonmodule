package pers.cxd.networklibrary;

import io.reactivex.rxjava3.disposables.Disposable;

public abstract class SimpleHttpCallback<D> implements HttpCallback<D> {

    @Override
    public void addDisposable(Disposable disposable) {

    }

}
