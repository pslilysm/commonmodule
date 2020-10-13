package pers.cxd.utillibrary;

public class Log {

    public static boolean DEBUG_MODE = true;

    public static final String TAG = "DEBUG_CXD_";

    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    public static String getStackTraceString(Throwable tr) {
        return "";
    }

    /**
     * Interface to handle terrible failures from {@link #wtf}.
     *
     * @hide
     */

    private Log() {
    }

    public static boolean isLoggable(String a, int b){
        return false;
    }

    /**
     * Send a {@link #VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (DEBUG_MODE) android.util.Log.v(tag, msg);
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (DEBUG_MODE) android.util.Log.v(tag, msg, tr);
    }

    /**
     * Send a {@link #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (DEBUG_MODE) android.util.Log.d(tag, msg);
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG_MODE) android.util.Log.d(tag, msg,tr);
    }

    /**
     * Send an {@link #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (DEBUG_MODE) android.util.Log.i(tag, msg);
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG_MODE) android.util.Log.i(tag, msg, tr);
    }

    /**
     * Send a {@link #WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (DEBUG_MODE) android.util.Log.w(tag, msg);
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG_MODE) android.util.Log.w(tag, msg,tr);
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     *
     *  The default level of any tag is set to INFO. This means that any level above and including
     *  INFO will be logged. Before you make any calls to a logging method you should check to see
     *  if your tag should be logged. You can change the default level by setting a system property:
     *      'setprop log.tag.&lt;YOUR_LOG_TAG> &lt;LEVEL>'
     *  Where level is either VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, or SUPPRESS. SUPPRESS will
     *  turn off all logging for your tag. You can also create a local.prop file that with the
     *  following in it:
     *      'log.tag.&lt;YOUR_LOG_TAG>=&lt;LEVEL>'
     *  and place that in /data/local.prop.
     *
     * @param tag The tag to check.
     * @param level The level to check.
     * @return Whether or not that this is allowed to be logged.
     * @throws IllegalArgumentException is thrown if the tag.length() > 23
     *         for Nougat (7.0) releases (API <= 23) and prior, there is no
     *         tag limit of concern after this API level.
     */


    /*
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr) {
        if (DEBUG_MODE) android.util.Log.w(tag, tr);
    }

    /**
     * Send an {@link #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (DEBUG_MODE) android.util.Log.e(tag, msg);
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG_MODE) android.util.Log.e(tag, msg, tr);
    }


}
