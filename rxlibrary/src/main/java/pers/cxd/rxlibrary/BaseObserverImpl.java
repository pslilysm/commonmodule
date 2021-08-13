package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseObserverImpl<D> implements Observer<D> {

    private final CompositeDisposable mSubscription;
    private Disposable disposable;

    public BaseObserverImpl(@Nullable CompositeDisposable subscription) {
        this.mSubscription = subscription;
    }
    
    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        this.disposable = disposable;
        if (mSubscription != null){
            mSubscription.add(disposable);
        }
    }

    @Override
    public void onComplete() {
        dispose();
    }

    /**
     * cancel the subscribe to prevent mem leak;
     */
    protected void dispose(){
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }

}
