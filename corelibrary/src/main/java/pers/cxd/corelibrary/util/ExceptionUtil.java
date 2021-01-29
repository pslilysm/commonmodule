package pers.cxd.corelibrary.util;

public class ExceptionUtil {

    public static Throwable getRealCause(Throwable throwable){
        Throwable cause = throwable;
        while (cause.getCause() != null){
            cause = cause.getCause();
        }
        return cause;
    }

    public static void rethrow(Throwable throwable){
        if (throwable instanceof RuntimeException){
            throw (RuntimeException) throwable;
        }else {
            throw new RuntimeException(throwable);
        }
    }

}
