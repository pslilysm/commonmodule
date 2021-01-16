package pers.cxd.corelibrary.util;

import com.google.gson.Gson;

public class GsonUtil {

    private static final Gson sGson = new Gson();

    public static String objToString(Object obj){
        return sGson.toJson(obj);
    }

    public static <T> T strToObject(String str, Class<T> tClass){
        return sGson.fromJson(str, tClass);
    }

}
