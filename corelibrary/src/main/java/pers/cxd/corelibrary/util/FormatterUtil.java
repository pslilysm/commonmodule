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
        assert sdf != null;
        sdf.applyPattern(syyyyMMddHHmmssPatter);
        return sdf;
    }

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"MM-dd"}
     */
    public static SimpleDateFormat getMMddFormatter(){
        SimpleDateFormat sdf = sDateFormatTLS.get();
        assert sdf != null;
        sdf.applyPattern(sMMddPattern);
        return sdf;
    }

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"HH:mm:ss"}
     */
    public static SimpleDateFormat getHHmmssFormatter(){
        SimpleDateFormat sdf = sDateFormatTLS.get();
        assert sdf != null;
        sdf.applyPattern(sHHmmssPattern);
        return sdf;
    }

    private static final ThreadLocal<DecimalFormat> _0dot000Formatter = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.000");
        }
    };

    private static final ThreadLocal<DecimalFormat> _0dot00Formatter = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.00");
        }
    };

    private static final ThreadLocal<DecimalFormat> _0dot0Formatter = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.0");
        }
    };

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.000"}
     */
    public static DecimalFormat get_0dot000Formatter() {
        return _0dot000Formatter.get();
    }

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.00"}
     */
    public static DecimalFormat get_0dot00Formatter(){
        return _0dot00Formatter.get();
    }

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.0"}
     */
    public static DecimalFormat get_0dot0Formatter(){
        return _0dot0Formatter.get();
    }

}
