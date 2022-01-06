package pers.cxd.corelibrary.util;

import android.text.TextUtils;

import androidx.collection.ArraySet;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pers.cxd.corelibrary.annotation.GsonExclude;

/**
 * Gson工具类
 */
public class GsonUtil {

    static {
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                GsonExclude gsonExclude = f.getAnnotation(GsonExclude.class);
                return gsonExclude != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        sPrettyGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().setExclusionStrategies(strategy).create();
        sGson = new GsonBuilder().disableHtmlEscaping().setExclusionStrategies(strategy).create();
    }

    private static final Gson sPrettyGson;

    private static final Gson sGson;

    public static String objToJson(Object obj){
        return objToJson(obj, false);
    }

    public static String objToJson(Object obj, boolean pretty){
        return pretty ? sPrettyGson.toJson(obj) : sGson.toJson(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> tClass){
        return sGson.fromJson(json, tClass);
    }

    public static <T> Set<T> jsonToSet(String str, Class<T> tClass){
        Set<T> list = new ArraySet<>();
        if (!TextUtils.isEmpty(str)) {
            JsonArray array = JsonParser.parseString(str).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(sGson.fromJson(elem, tClass));
            }
        }
        return list;
    }

    public static <T> List<T> jsonToList(String str, Class<T> tClass){
        List<T> list = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            JsonArray array = JsonParser.parseString(str).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(sGson.fromJson(elem, tClass));
            }
        }
        return list;
    }

    public static <T> Map<String, T> jsonToMap(String json, Class<T> tClass) {
        return jsonToMap(json, String.class, tClass);
    }

    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> kClass, Class<V> vClass) {
        if (TextUtils.isEmpty(json)) {
            return new LinkedTreeMap<>();
        }
        Type empMapType = new TypeToken<LinkedTreeMap<K, V>>() {}.getType();
        return sGson.fromJson(json, empMapType);
    }

    public static String prettyJson(String jsonStr){
        JsonElement je = JsonParser.parseString(jsonStr);
        return sPrettyGson.toJson(je);
    }

}
