package pers.cxd.corelibrary.util;

import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Set;

import pers.cxd.corelibrary.ApplicationHolder;

public class MMKVUtil {

    static {
        MMKV.initialize(ApplicationHolder.get());
    }

    private static final MMKV sDefault = MMKV.defaultMMKV();

    /**
     * encode a non-null value
     */
    public static void encode(String key, Object value){
        encode(key, value, null);
    }

    /**
     * encode a null value
     */
    public static void encode(String key, Class<?> valueClass){
        encode(key, null, valueClass);
    }

    private static void encode(String key, Object value, Class<?> valueClass){
        Class<?> clazz = value == null ? valueClass : value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            sDefault.encode(key, (int) value);
        } else if (clazz == float.class || clazz == Float.class){
            sDefault.encode(key, (float)value);
        } else if (clazz == double.class || clazz == Double.class){
            sDefault.encode(key, (double)value);
        } else if (clazz == long.class || clazz == Long.class){
            sDefault.encode(key, (long)value);
        } else if (clazz == boolean.class || clazz == Boolean.class){
            sDefault.encode(key, (boolean)value);
        } else if (clazz == String.class){
            sDefault.encode(key, (String)value);
        } else if (value instanceof Set){
            sDefault.encode(key, (Set<String>) value);
        } else if (clazz == byte[].class){
            sDefault.encode(key, (byte[]) value);
        } else if (value instanceof Parcelable){
            sDefault.encode(key, (Parcelable) value);
        } else {
            throw new UnsupportedOperationException(clazz + " can't encode");
        }
    }

    /**
     * @param key mapped value
     * @param defaultValue will be return if not find the key
     * @return saved value by key
     */
    public static <T> T decode(String key, T defaultValue){
        return decode(key, defaultValue, null);
    }

    /**
     * @param key mapped value
     * @param valueClass the returned value will instance of valueClass
     * @return null if not found the key
     */
    public static <T> T decode(String key, Class<T> valueClass){
        return decode(key, null, valueClass);
    }

    private static <T> T decode(String key, T defaultValue, Class<T> valueClass){
        Class<?> clazz = defaultValue == null ? valueClass : defaultValue.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(sDefault.decodeInt(key, (Integer) defaultValue));
        } else if (clazz == float.class || clazz == Float.class){
            return (T) Float.valueOf(sDefault.decodeFloat(key, (Float) defaultValue));
        } else if (clazz == double.class || clazz == Double.class){
            return (T) Double.valueOf(sDefault.decodeDouble(key, (Double) defaultValue));
        } else if (clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(sDefault.decodeLong(key, (Long) defaultValue));
        } else if (clazz == boolean.class || clazz == Boolean.class){
            return (T) Boolean.valueOf(sDefault.decodeBool(key, (Boolean) defaultValue));
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

    public static <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass){
        return decodeParcelable(key, tClass, null);
    }

    public static <T extends Parcelable> T decodeParcelable(String key, Class<T> tClass, T defaultValue){
        return sDefault.decodeParcelable(key, tClass, defaultValue);
    }

}
