package pers.cxd.networklibrary;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;

public interface HttpCallback<D> {

    void onSuccess(@NonNull D d);
    void addDisposable(Disposable disposable);
    void onNetworkFailure(Throwable e, String errMsg);

}
