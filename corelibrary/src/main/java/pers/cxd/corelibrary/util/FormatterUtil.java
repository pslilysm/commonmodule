package pers.cxd.corelibrary.util;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import pers.cxd.corelibrary.Singleton;

public class FormatterUtil {

    @SuppressLint("ConstantLocale")
    private static final Singleton<SimpleDateFormat> syyyyMMddHHmmss = new Singleton<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat create() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"yyyy-MM-dd HH:mm:ss"}
     */
    public static SimpleDateFormat getyyyyMMddHHmmssFormatter(){
        return syyyyMMddHHmmss.getInstance();
    }

    @SuppressLint("ConstantLocale")
    private static final Singleton<SimpleDateFormat> syyyyMMdd = new Singleton<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat create() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"yyyy-MM-dd"}
     */
    public static SimpleDateFormat getyyyyMMddFormatter(){
        return syyyyMMdd.getInstance();
    }

    @SuppressLint("ConstantLocale")
    private static final Singleton<SimpleDateFormat> sMMdd = new Singleton<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat create() {
            return new SimpleDateFormat("MM-dd", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"MM-dd"}
     */
    public static SimpleDateFormat getMMddFormatter(){
        return sMMdd.getInstance();
    }

    @SuppressLint("ConstantLocale")
    private static final Singleton<SimpleDateFormat> sHHmmss = new Singleton<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat create() {
            return new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        }
    };

    /**
     * @return the singleton of DateFormatter with pattern {"HH:mm:ss"}
     */
    public static SimpleDateFormat getHHmmssFormatter(){
        return sHHmmss.getInstance();
    }

    private static final Singleton<DecimalFormat> _0dot000Formatter = new Singleton<DecimalFormat>() {
        @Override
        protected DecimalFormat create() {
            return new DecimalFormat("0.000");
        }
    };

    private static final Singleton<DecimalFormat> _0dot00Formatter = new Singleton<DecimalFormat>() {
        @Override
        protected DecimalFormat create() {
            return new DecimalFormat("0.00");
        }
    };

    private static final Singleton<DecimalFormat> _0dot0Formatter = new Singleton<DecimalFormat>() {
        @Override
        protected DecimalFormat create() {
            return new DecimalFormat("0.0");
        }
    };

    /**
     * @return the singleton of DecimalFormat with pattern {"#.000"}
     */
    public static DecimalFormat get_0dot000Formatter() {
        return _0dot000Formatter.getInstance();
    }

    /**
     * @return the singleton of DecimalFormat with pattern {"#.00"}
     */
    public static DecimalFormat get_0dot00Formatter(){
        return _0dot00Formatter.getInstance();
    }

    /**
     * @return the singleton of DecimalFormat with pattern {"#.0"}
     */
    public static DecimalFormat get_0dot0Formatter(){
        return _0dot0Formatter.getInstance();
    }

}
