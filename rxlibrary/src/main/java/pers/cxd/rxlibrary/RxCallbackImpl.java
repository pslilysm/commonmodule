package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class RxCallbackImpl<D> implements RxCallback<D>  {

    private final CompositeDisposable mSubscription;

    public RxCallbackImpl(CompositeDisposable subscription) {
        this.mSubscription = subscription;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        mSubscription.add(disposable);
    }

}
