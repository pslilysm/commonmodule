package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseHttpObserverImpl<D> extends BaseObserverImpl<D> {

    public BaseHttpObserverImpl(@Nullable CompositeDisposable subscription) {
        super(subscription);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        dispose();
    }

}
