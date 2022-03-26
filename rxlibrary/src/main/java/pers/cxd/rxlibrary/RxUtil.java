package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Miscellaneous {@code RxJava} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class RxUtil {

    public static <D> void execute(@NonNull Observable<D> observable, Observer<D> observer, ObservableTransformer<D, D> transformer) {
        observable.compose(transformer)
                .subscribe(observer);
    }

    public static class Transformers {

        private static final ObservableTransformer IOToMain = upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

        private static final ObservableTransformer IO = upstream -> upstream.subscribeOn(Schedulers.io());

        private static final ObservableTransformer NON = upstream -> upstream;

        public static <T> ObservableTransformer<T, T> IOToMain() {
            return IOToMain;
        }

        public static <T> ObservableTransformer<T, T> IO() {
            return IO;
        }

        public static <T> ObservableTransformer<T, T> NON() {
            return NON;
        }

    }

}
