package pers.cxd.corelibrary.util;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import pers.cxd.corelibrary.Singleton;

public class FormatterUtil {

    @SuppressLint("ConstantLocale")
    private static final ThreadLocal<SimpleDateFormat> syyyyMMddHHmmssTLS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"yyyy-MM-dd HH:mm:ss"}
     */
    public static SimpleDateFormat getyyyyMMddHHmmssFormatter(){
        return syyyyMMddHHmmssTLS.get();
    }

    @SuppressLint("ConstantLocale")
    private static final ThreadLocal<SimpleDateFormat> syyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"yyyy-MM-dd"}
     */
    public static SimpleDateFormat getyyyyMMddFormatter(){
        return syyyyMMdd.get();
    }

    @SuppressLint("ConstantLocale")
    private static final ThreadLocal<SimpleDateFormat> sMMdd = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"MM-dd"}
     */
    public static SimpleDateFormat getMMddFormatter(){
        return sMMdd.get();
    }

    @SuppressLint("ConstantLocale")
    private static final ThreadLocal<SimpleDateFormat> sHHmmss = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"HH:mm:ss"}
     */
    public static SimpleDateFormat getHHmmssFormatter(){
        return sHHmmss.get();
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
     * @return the singleton of DecimalFormat with pattern {"#.000"}
     */
    public static DecimalFormat get_0dot000Formatter() {
        return _0dot000Formatter.get();
    }

    /**
     * @return the singleton of DecimalFormat with pattern {"#.00"}
     */
    public static DecimalFormat get_0dot00Formatter(){
        return _0dot00Formatter.get();
    }

    /**
     * @return the singleton of DecimalFormat with pattern {"#.0"}
     */
    public static DecimalFormat get_0dot0Formatter(){
        return _0dot0Formatter.get();
    }

}
