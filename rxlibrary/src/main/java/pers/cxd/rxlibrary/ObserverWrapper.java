package pers.cxd.rxlibrary;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ObserverWrapper<D> implements Observer<D> {

    private final Observer<D> mBase;

    public ObserverWrapper(Observer<D> mBase) {
        this.mBase = mBase;
    }

    @Override
    public void onNext(@NonNull D d) {
        mBase.onNext(d);
    }

    @Override
    public void onSubscribe(@NotNull Disposable disposable) {
        mBase.onSubscribe(disposable);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        mBase.onError(e);
    }

    @Override
    public void onComplete() {
        mBase.onComplete();
    }

}
