package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class RxCallbackImpl<D> implements RxCallback<D>  {

    private final CompositeDisposable mSubscription;
    Disposable disposable;

    public RxCallbackImpl(@Nullable CompositeDisposable subscription) {
        this.mSubscription = subscription;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
        if (mSubscription != null){
            mSubscription.add(disposable);
        }
    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
