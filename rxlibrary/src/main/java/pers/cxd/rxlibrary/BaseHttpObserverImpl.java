package pers.cxd.rxlibrary;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * Default Http Observer
 *
 * @param <D> the type of item the Observer expects to observe
 * @author pslilysm
 * @since 1.0.0
 */
public abstract class BaseHttpObserverImpl<D> extends BaseObserverImpl<D> {

    public BaseHttpObserverImpl(@Nullable CompositeDisposable subscription) {
        super(subscription);
    }

    /**
     * when onError, will calling {@code onComplete()}
     */
    @Override
    public void onError(@NonNull Throwable e) {
        onComplete();
    }

}
