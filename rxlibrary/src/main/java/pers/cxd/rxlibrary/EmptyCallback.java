package pers.cxd.rxlibrary;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;
import pers.cxd.corelibrary.Singleton;

public abstract class EmptyCallback implements RxCallback {

    private static final Singleton<EmptyCallback> sEmptyCallback = new Singleton<EmptyCallback>() {
        @Override
        protected EmptyCallback create() {
            return new EmptyCallback() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }

                @Override
                public void onSuccess(@NonNull Object o) {

                }
            };
        }
    };

    public static EmptyCallback create(){
        return sEmptyCallback.getInstance();
    }

}
