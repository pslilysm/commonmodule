package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pers.cxd.corelibrary.util.ExceptionUtil;

public class RxUtil {

    public static class Transformers {

        private static final ObservableTransformer IOToMain = new ObservableTransformer() {
            @Override
            public @NonNull ObservableSource apply(@NonNull Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

        private static final ObservableTransformer IO = new ObservableTransformer() {
            @Override
            public @NonNull ObservableSource apply(@NonNull Observable upstream) {
                return upstream.subscribeOn(Schedulers.io());
            }
        };

        private static final ObservableTransformer NON = new ObservableTransformer() {
            @Override
            public @NonNull ObservableSource apply(@NonNull Observable upstream) {
                return upstream;
            }
        };

        public static <T> ObservableTransformer<T, T> IOToMain(){
            return IOToMain;
        }

        public static <T> ObservableTransformer<T, T> IO(){
            return IO;
        }

        public static <T> ObservableTransformer<T, T> NON(){
            return NON;
        }

    }

    public static <D> void execute(@NonNull RxCallback<D> callback, Observable<D> observable, ObservableTransformer<D, D> transformer){
        observable.compose(transformer)
                .subscribe(aObserverInjectRxCallback(callback));
    }
    
    public static <D> Observer<D> aObserverInjectRxCallback(@NonNull RxCallback<D> callback){
        return new Observer<D>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                callback.onSubscribe(d);
            }

            @Override
            public void onNext(@NonNull D d) {
                callback.onSuccess(d);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (!callback.handleError(e)) {
                    // in release version, we normally use bugly or another sdk to report this error;
                    // so always make your handleAnotherError return true;
                    ExceptionUtil.rethrow(e);
                }
            }

            @Override
            public void onComplete() {
                callback.onComplete();
            }

        };
    }

}
