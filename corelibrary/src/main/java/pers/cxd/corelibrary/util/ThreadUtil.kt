package pers.cxd.corelibrary.util

import android.os.Looper

/**
 * Miscellaneous [Thread] utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object ThreadUtil {
    /**
     * check current thread is main thread
     *
     * @throws IllegalStateException if current thread is not main thread
     */
    fun checkIsMainThread() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "cur thread is " + Thread.currentThread().name + ", not main thread" }
    }

    /**
     * check current thread not main thread
     *
     * @throws IllegalStateException if current thread is main thread
     */
    fun throwIfMainThread() {
        check(Looper.getMainLooper() != Looper.myLooper()) { "cur thread is main thread" }
    }
}