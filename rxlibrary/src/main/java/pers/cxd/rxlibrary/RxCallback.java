package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

public interface RxCallback<D> {

    void onSuccess(@NonNull D d);
    void onSubscribe(Disposable disposable);
    default boolean handleError(Throwable e){
        return false;
    }
    void onComplete();

}
