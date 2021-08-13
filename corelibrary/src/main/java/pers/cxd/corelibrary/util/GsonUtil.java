package pers.cxd.corelibrary.util;

import android.util.Log;

import androidx.collection.ArraySet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Gson工具类
 */
public class GsonUtil {

    private static final Gson sPrettyGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final Gson sGson = new GsonBuilder().disableHtmlEscaping().create();

    private static final JsonParser sJsonParser = new JsonParser();

    public static String objToString(Object obj){
        return objToString(obj, true);
    }

    public static String objToString(Object obj, boolean pretty){
        return pretty ? sPrettyGson.toJson(obj) : sGson.toJson(obj);
    }

    public static <T> T strToObject(String str, Class<T> tClass){
        return sGson.fromJson(str, tClass);
    }

    public static <T> Set<T> strToSet(String str, Class<T> tClass){
        Set<T> list = new ArraySet<>();
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

    public static <T> Map<String, T> jsonStrToMap(String json, Class<T> tClass) {
        return strToMap(json, String.class, tClass);
    }

    public static <K, V> Map<K, V> strToMap(String json, Class<K> kClass, Class<V> vClass) {
        Map<K, V> map = null;
        Type empMapType = new TypeToken<LinkedHashMap<K, V>>() {}.getType();
        try{
            map = sGson.fromJson(json, empMapType);
        }catch (Exception e){
            Log.e("GsonUtil", "strToMap: " + e.getMessage());
        }
        if (map == null) map = new LinkedHashMap<>();
        return map;
    }

    public static String prettyJson(String jsonStr){
        JsonElement je = sJsonParser.parse(jsonStr);
        return sPrettyGson.toJson(je);
    }

}
