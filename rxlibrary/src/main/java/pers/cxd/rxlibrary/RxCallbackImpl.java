package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class RxCallbackImpl<D> implements RxCallback<D>  {

    private final CompositeDisposable mSubscription;
    Disposable disposable;

    public RxCallbackImpl(CompositeDisposable subscription) {
        this.mSubscription = subscription;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
        mSubscription.add(disposable);
    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
