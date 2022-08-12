package pers.cxd.rxlibrary

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Miscellaneous `RxJava` utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object RxUtil {
    fun <D : Any> execute(
        observable: Observable<D>,
        observer: Observer<D>,
        transformer: ObservableTransformer<D, D>
    ) {
        observable.compose(transformer)
            .subscribe(observer)
    }

    object Transformers {
        private val IOToMain: ObservableTransformer<Any, Any> =
            ObservableTransformer<Any, Any> { upstream: Observable<Any> ->
                upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        private val IO: ObservableTransformer<Any, Any> =
            ObservableTransformer<Any, Any> { upstream: Observable<Any> -> upstream.subscribeOn(Schedulers.io()) }
        private val NON: ObservableTransformer<Any, Any> =
            ObservableTransformer<Any, Any> { upstream: Observable<Any> -> upstream }

        fun <T> IOToMain(): ObservableTransformer<T, T> {
            return IOToMain as ObservableTransformer<T, T>
        }

        fun <T> IO(): ObservableTransformer<T, T> {
            return IO as ObservableTransformer<T, T>
        }

        fun <T> NON(): ObservableTransformer<T, T> {
            return NON as ObservableTransformer<T, T>
        }
    }
}