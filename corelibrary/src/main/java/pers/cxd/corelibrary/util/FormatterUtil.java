package pers.cxd.corelibrary.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormatterUtil {

    private static final ThreadLocal<SimpleDateFormat> sDateFormatTLS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("", Locale.getDefault());
        }
    };

    private static final String syyyyMMddHHmmssPatter = "yyyy-MM-dd HH:mm:ss";

    private static final String sMMddPattern = "MM-dd";

    private static final String sHHmmssPattern = "HH:mm:ss";

    /**
     * @return a Thread-Safely SimpleDateFormat
     */
    public static SimpleDateFormat getSimpleDateFormatter() {
        return sDateFormatTLS.get();
    }

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"yyyy-MM-dd HH:mm:ss"}
     */
    public static SimpleDateFormat getyyyyMMddHHmmssFormatter(){
        SimpleDateFormat sdf = sDateFormatTLS.get();
        sdf.applyPattern(syyyyMMddHHmmssPatter);
        return sdf;
    }

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"MM-dd"}
     */
    public static SimpleDateFormat getMMddFormatter(){
        SimpleDateFormat sdf = sDateFormatTLS.get();
        sdf.applyPattern(sMMddPattern);
        return sdf;
    }

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"HH:mm:ss"}
     */
    public static SimpleDateFormat getHHmmssFormatter(){
        SimpleDateFormat sdf = sDateFormatTLS.get();
        sdf.applyPattern(sHHmmssPattern);
        return sdf;
    }

    private static final ThreadLocal<DecimalFormat> sDecimalFormatTLS = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat();
        }
    };

    /**
     * @return a Thread-Safely DecimalFormat
     */
    public static DecimalFormat getDecimalFormat() {
        return sDecimalFormatTLS.get();
    }

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.000"}
     */
    public static DecimalFormat get_0dot000Formatter() {
        DecimalFormat decimalFormat = sDecimalFormatTLS.get();
        decimalFormat.applyPattern("0.000");
        return decimalFormat;
    }

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.00"}
     */
    public static DecimalFormat get_0dot00Formatter(){
        DecimalFormat decimalFormat = sDecimalFormatTLS.get();
        decimalFormat.applyPattern("0.00");
        return decimalFormat;
    }

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.0"}
     */
    public static DecimalFormat get_0dot0Formatter(){
        DecimalFormat decimalFormat = sDecimalFormatTLS.get();
        decimalFormat.applyPattern("0.0");
        return decimalFormat;
    }

}
