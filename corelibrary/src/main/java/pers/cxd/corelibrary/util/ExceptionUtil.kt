package pers.cxd.corelibrary.util

/**
 * Miscellaneous [Exception] utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object ExceptionUtil {
    /**
     * Get thr root cause of the `throwable`
     *
     * @param throwable to get the root cause
     * @return the root cause
     */
    @kotlin.jvm.JvmStatic
    fun getRootCause(throwable: Throwable?): Throwable {
        var cause = throwable
        while (cause!!.cause != null) {
            cause = cause.cause
        }
        return cause
    }

    /**
     * Rethrow a throwable as a [RuntimeException]
     *
     * @param throwable to throw
     * @return a RuntimeException wrapped the original throwable
     */
    @kotlin.jvm.JvmStatic
    fun rethrow(throwable: Throwable): RuntimeException {
        return if (throwable is RuntimeException) {
            throwable
        } else {
            RuntimeException(throwable)
        }
    }
}