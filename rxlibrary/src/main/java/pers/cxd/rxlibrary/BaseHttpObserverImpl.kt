package pers.cxd.rxlibrary

import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Default Http Observer
 *
 * @param <D> the type of item the Observer expects to observe
 * @author pslilysm
 * @since 1.0.0
</D> */
abstract class BaseHttpObserverImpl<D>(subscription: CompositeDisposable?) :
    BaseObserverImpl<D>(subscription) {
    /**
     * calling `onComplete()` while error occurs
     */
    override fun onError(e: Throwable) {
        onComplete()
    }
}