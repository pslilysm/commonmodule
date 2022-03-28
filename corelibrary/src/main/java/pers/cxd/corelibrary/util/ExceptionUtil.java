package pers.cxd.corelibrary.util;

/**
 * Miscellaneous {@link Exception} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class ExceptionUtil {

    /**
     * Get thr root cause of the {@code throwable}
     *
     * @param throwable to get the root cause
     * @return the root cause
     */
    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

    /**
     * Rethrow a throwable as a {@link RuntimeException}
     *
     * @param throwable to throw
     * @return a RuntimeException wrapped the original throwable
     */
    public static RuntimeException rethrow(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            return (RuntimeException) throwable;
        } else {
            return new RuntimeException(throwable);
        }
    }

}
