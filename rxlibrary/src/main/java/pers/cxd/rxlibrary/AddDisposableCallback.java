package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class AddDisposableCallback<D> extends RxCallbackWrapper<D>{

    private CompositeDisposable mSubscription;

    public AddDisposableCallback(RxCallback<D> mBase, CompositeDisposable mSubscription) {
        super(mBase);
        this.mSubscription = mSubscription;
    }

    @Override
    public void onSubscribe(Disposable disposable) {
        super.onSubscribe(disposable);
        mSubscription.add(disposable);
    }
}
