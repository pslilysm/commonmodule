package pers.cxd.rxlibrary;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;

public interface RxCallback<D> {

    void onSubscribe(Disposable disposable);
    void onSuccess(@NonNull D d);
    default boolean handleError(Throwable e){
        return true;
    }
    default void onComplete(){

    }

}
