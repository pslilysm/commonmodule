package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxUtil {

    public static class TransFormers{

        private static ObservableTransformer IOToMain = new ObservableTransformer() {
            @Override
            public @NonNull ObservableSource apply(@NonNull Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

        private static ObservableTransformer IO = new ObservableTransformer() {
            @Override
            public @NonNull ObservableSource apply(@NonNull Observable upstream) {
                return upstream.subscribeOn(Schedulers.io());
            }
        };

        public static ObservableTransformer IOToMain(){
            return IOToMain;
        }

        public static ObservableTransformer IO(){
            return IO;
        }

    }

    public static <D> void execute(RxCallback<D> callback, Observable<D> observable, ObservableTransformer transformer){
        observable.compose(transformer)
                .subscribe(aObserverInjectRxCallback(callback));
    }
    
    public static <D> Observer<D> aObserverInjectRxCallback(RxCallback<D> callback){
        return new Observer<D>() {
            Disposable disposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
                callback.onSubscribe(d);
            }

            @Override
            public void onNext(@NonNull D d) {
                callback.onSuccess(d);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (!callback.handleError(e)){
                    // in release version, we normally use bugly or another sdk to report this error;
                    // so always make your handleAnotherError return true;
                    throw new RuntimeException(e);
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                callback.onComplete();
                dispose();
            }

            private void dispose(){
                if (disposable != null && !disposable.isDisposed()){
                    disposable.dispose();
                    disposable = null;
                }
            }

        };
    }

}
