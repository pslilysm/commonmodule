package pers.cxd.rxlibrary;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;

public class RxCallbackWrapper<D> implements RxCallback<D> {

    private final RxCallback<D> mBase;

    public RxCallbackWrapper(RxCallback<D> mBase) {
        this.mBase = mBase;
    }

    @Override
    public void onSuccess(@NonNull D d) {
        mBase.onSuccess(d);
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        mBase.onSubscribe(disposable);
    }

    @Override
    public boolean handleError(Throwable e) {
        return mBase.handleError(e);
    }

    @Override
    public void onComplete() {
        mBase.onComplete();
    }

}
