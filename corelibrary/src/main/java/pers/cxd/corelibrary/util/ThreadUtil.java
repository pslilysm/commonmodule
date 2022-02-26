package pers.cxd.corelibrary.util;

import android.os.Looper;

/**
 * Miscellaneous {@link Thread} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class ThreadUtil {

    /**
     * check current thread is main thread
     *
     * @throws IllegalStateException if current thread is not main thread
     */
    public static void checkIsMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("cur thread is " + Thread.currentThread().getName() + ", not main thread");
        }
    }

    /**
     * check current thread not main thread
     *
     * @throws IllegalStateException if current thread is main thread
     */
    public static void throwIfMainThread() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IllegalStateException("cur thread is main thread");
        }
    }

}
