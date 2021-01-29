package pers.cxd.corelibrary.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class GsonUtil {

    private static final Gson sGson = new Gson();

    private static final JsonParser sJsonParser = new JsonParser();

    public static String objToString(Object obj){
        return sGson.toJson(obj);
    }

    public static <T> T strToObject(String str, Class<T> tClass){
        return sGson.fromJson(str, tClass);
    }

    public static <T> List<T> strToList(String str, Class<T> tClass){
        List<T> list = new ArrayList<>();
        try {
            JsonArray array = sJsonParser.parse(str).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(sGson.fromJson(elem, tClass));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
