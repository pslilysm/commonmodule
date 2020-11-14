package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import pers.cxd.corelibrary.Singleton;

public class RxUtil {

    private static Singleton<ObservableTransformer> sIOToMainTransformer = new Singleton<ObservableTransformer>() {
        @Override
        protected ObservableTransformer create() {
            return new ObservableTransformer() {
                @Override
                public @NonNull ObservableSource apply(@NonNull Observable upstream) {
                    return upstream.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }
    };

    private static Singleton<ObservableTransformer> sIOTransformer = new Singleton<ObservableTransformer>() {
        @Override
        protected ObservableTransformer create() {
            return new ObservableTransformer() {
                @Override
                public @NonNull ObservableSource apply(@NonNull Observable upstream) {
                    return upstream.subscribeOn(Schedulers.io());
                }
            };
        }
    };

    public static ObservableTransformer getIOToMainTransformer(){
        return sIOToMainTransformer.getInstance();
    }

    public static ObservableTransformer getIOTransformer(){
        return sIOTransformer.getInstance();
    }

    public static <D> void execute(RxCallback<D> callback, Observable<D> observable, ObservableTransformer transformer){
        observable.compose(transformer)
                .subscribe(new Observer<D>() {
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

                });
    }

}
