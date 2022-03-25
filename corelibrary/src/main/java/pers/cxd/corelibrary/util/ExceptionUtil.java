package pers.cxd.corelibrary.util;

/**
 * Miscellaneous {@link Exception} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class ExceptionUtil {

    public static Throwable getRealCause(Throwable throwable) {
        Throwable cause = throwable;
        while (cause.getCause() != null){
            cause = cause.getCause();
        }
        return cause;
    }

    public static void rethrow(Throwable throwable) {
        if (throwable instanceof RuntimeException){
            throw (RuntimeException) throwable;
        }else {
            throw new RuntimeException(throwable);
        }
    }

}
