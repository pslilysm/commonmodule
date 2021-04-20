package pers.cxd.rxlibrary;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseObserverImpl<D> implements Observer<D> {

    private final CompositeDisposable mSubscription;
    Disposable disposable;

    public BaseObserverImpl(@Nullable CompositeDisposable subscription) {
        this.mSubscription = subscription;
    }
    
    @Override
    public void onSubscribe(@NotNull Disposable disposable) {
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
