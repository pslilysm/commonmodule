package pers.cxd.corelibrary.util;

import com.google.gson.Gson;

public class GsonUtil {

    private static final Gson sGson = new Gson();

    public static String objToString(Object obj){
        return sGson.toJson(obj);
    }

}
