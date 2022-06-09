package pers.cxd.corelibrary.util;

import java.util.Collection;
import java.util.Map;

/**
 * Miscellaneous {@code Collection} and {@code Map} and {@code Array} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class DataStructureUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean haveElement(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean haveElement(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static boolean haveElement(boolean[] arr) {
        return arr != null && arr.length > 0;
    }

    public static boolean haveElement(byte[] arr) {
        return arr != null && arr.length > 0;
    }

    public static boolean haveElement(char[] arr) {
        return arr != null && arr.length > 0;
    }

    public static boolean haveElement(short[] arr) {
        return arr != null && arr.length > 0;
    }

    public static boolean haveElement(int[] arr) {
        return arr != null && arr.length > 0;
    }

    public static boolean haveElement(float[] arr) {
        return arr != null && arr.length > 0;
    }

    public static boolean haveElement(long[] arr) {
        return arr != null && arr.length > 0;
    }

    public static boolean haveElement(double[] arr) {
        return arr != null && arr.length > 0;
    }

    public static <T> boolean haveElement(T[] arr) {
        return arr != null && arr.length > 0;
    }

}
