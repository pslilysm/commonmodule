package pers.cxd.rxlibrary

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

/**
 * Default implementation of the [Observer]
 *
 * @param <D> the type of item the Observer expects to observe
 * @author pslilysm
 * @since 1.0.0
</D> */
abstract class BaseObserverImpl<D>(private val mSubscription: CompositeDisposable?) : Observer<D> {
    private var disposable: Disposable? = null
    override fun onSubscribe(disposable: Disposable) {
        this.disposable = disposable
        mSubscription?.add(disposable)
    }

    override fun onComplete() {
        dispose()
    }

    /**
     * cancel the subscribe to prevent mem leak;
     */
    protected fun dispose() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }
}