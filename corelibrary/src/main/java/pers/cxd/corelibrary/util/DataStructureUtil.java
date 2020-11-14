package pers.cxd.corelibrary.util;

import java.util.Collection;
import java.util.Map;

public class DataStructureUtil {

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean hasElement(Collection<?> collection){
        return !isEmpty(collection);
    }

    public static boolean hasElement(Map<?, ?> map){
        return map != null && !map.isEmpty();
    }

    public static boolean hasElement(int[] arr){
        return arr != null && arr.length > 0;
    }

    public static boolean hasElement(Object[] arr){
        return arr != null && arr.length > 0;
    }

    public static boolean hasElement(double[] arr) {
        return arr != null && arr.length > 0;
    }
}
