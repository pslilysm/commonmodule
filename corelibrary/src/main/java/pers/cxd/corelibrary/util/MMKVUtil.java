package pers.cxd.corelibrary.util;

import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

import pers.cxd.corelibrary.AppHolder;

/**
 * Miscellaneous {@link MMKV} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class MMKVUtil {

    static {
        MMKV.initialize(AppHolder.get());
    }

    private static final MMKV sDefault = MMKV.defaultMMKV();

    /**
     * encode a non-null value
     * @see #encode(String, Object, Class)
     *
     * @param key mapped value
     * @param value mapped key
     */
    public static void encode(String key, Object value){
        encode(key, value, null);
    }

    /**
     * encode a null value
     * @see #encode(String, Object, Class)
     *
     * @param key mapped value
     * @param valueClass value's class(such as String, Integer, Double}
     */
    public static void encode(String key, Class<?> valueClass){
        encode(key, null, valueClass);
    }

    private static void encode(String key, Object value, Class<?> valueClass){
        Class<?> clazz = value != null ? value.getClass() : valueClass;
        if (clazz == Integer.class){
            sDefault.encode(key, (Integer) value);
        } else if (clazz == Float.class){
            sDefault.encode(key, (Float)value);
        } else if (clazz == Double.class){
            sDefault.encode(key, (Double)value);
        } else if (clazz == Long.class){
            sDefault.encode(key, (Long)value);
        } else if (clazz == Boolean.class){
            sDefault.encode(key, (Boolean)value);
        } else if (clazz == String.class){
            sDefault.encode(key, (String)value);
        } else if (clazz == Set.class){
            sDefault.encode(key, (Set<String>) value);
        } else if (clazz == byte[].class){
            sDefault.encode(key, (byte[]) value);
        } else if (Parcelable.class.isAssignableFrom(clazz)){
            sDefault.encode(key, (Parcelable) value);
        } else {
            throw new UnsupportedOperationException(clazz + " can't encode");
        }
    }

    /**
     * Decode the value mapped by the {@code key}
     * @see #decode(String, Object, Class)
     *
     * @param key mapped value
     * @param defaultValue will be return if not find the key
     * @return saved value by key
     */
    public static <T> T decode(String key, T defaultValue){
        return decode(key, defaultValue, null);
    }

    /**
     * Decode the value mapped by the {@code key}
     * @see #decode(String, Object, Class)
     *
     * @param key mapped value
     * @param valueClass the returned value will instance of valueClass
     * @return null if not found the key
     */
    public static <T> T decode(String key, Class<T> valueClass){
        return decode(key, null, valueClass);
    }

    private static <T> T decode(String key, T defaultValue, Class<T> valueClass){
        Class<?> clazz = defaultValue != null ? defaultValue.getClass() : valueClass;
        if (clazz == Integer.class){
            return (T) Integer.valueOf(sDefault.decodeInt(key, defaultValue == null ? 0 : (Integer) defaultValue));
        } else if (clazz == Float.class){
            return (T) Float.valueOf(sDefault.decodeFloat(key, defaultValue == null ? 0 : (Float) defaultValue));
        } else if (clazz == Double.class){
            return (T) Double.valueOf(sDefault.decodeDouble(key, defaultValue == null ? 0 : (Double) defaultValue));
        } else if (clazz == Long.class){
            return (T) Long.valueOf(sDefault.decodeLong(key, defaultValue == null ? 0 : (Long) defaultValue));
        } else if (clazz == Boolean.class){
            return (T) Boolean.valueOf(sDefault.decodeBool(key, defaultValue == null ? false : (Boolean) defaultValue));
        } else if (clazz == String.class){
            return (T) sDefault.decodeString(key, (String) defaultValue);
        } else if (defaultValue instanceof Set){
            return (T) sDefault.decodeStringSet(key, (Set<String>) defaultValue);
        } else if (clazz == byte[].class){
            return (T) sDefault.decodeBytes(key, (byte[]) defaultValue);
        } else {
            throw new UnsupportedOperationException(clazz + " can't decode");
        }
    }

    /**
     * Decode {@link Parcelable} by the key
     * @see #decodeParcelable(String, Class, Parcelable)
     *
     * @param key mapped parcelable value
     * @param tClass value's class
     * @param <T> the type of the value and must extends {@link Parcelable}
     * @return a parcelable value by the key
     */
    public static <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass){
        return decodeParcelable(key, tClass, null);
    }

    /**
     * Decode {@link Parcelable} by the key
     *
     * @param key mapped parcelable value
     * @param tClass value's class
     * @param defaultValue if we didn't find the value by the key, then {@code defaultValue} returned
     * @param <T> the type of the value and must extends {@link Parcelable}
     * @return a parcelable value by the key
     */
    public static <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass, T defaultValue){
        return sDefault.decodeParcelable(key, tClass, defaultValue);
    }

}
