package pers.cxd.rxlibrary;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;

public interface RxCallback<D> {

    void onSuccess(@NonNull D d);
    void onSubscribe(Disposable disposable);
    default boolean handleError(Throwable e){
        return true;
    }
    default void onComplete(){}

}
