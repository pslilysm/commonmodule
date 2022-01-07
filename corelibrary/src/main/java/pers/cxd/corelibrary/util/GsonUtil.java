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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pers.cxd.corelibrary.annotation.GsonExclude;

/**
 * Miscellaneous {@link Gson} utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
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

    /**
     * @see #objToJson(Object, boolean)
     *
     * @return a json string without pretty
     */
    public static String objToJson(Object obj){
        return objToJson(obj, false);
    }

    /**
     * Serialize object to json
     *
     * @param obj to serialize
     * @param pretty ture we'll returned a pretty json string
     * @return json string
     */
    public static String objToJson(Object obj, boolean pretty){
        return pretty ? sPrettyGson.toJson(obj) : sGson.toJson(obj);
    }

    /**
     * Deserialize json to object
     *
     * @param json json string
     * @param tClass class of object will returned
     * @param <T> the type of the object returned
     * @return a object deserialize of json string
     */
    public static <T> T jsonToObject(String json, Class<T> tClass){
        return sGson.fromJson(json, tClass);
    }

    /**
     * Deserialize json to {@link Set}
     *
     * @param json json string
     * @param tClass Set's elements class
     * @param <T> the type of the Set's element
     * @return a empty Set if json is empty string or empty array
     */
    public static <T> Set<T> jsonToSet(String json, Class<T> tClass){
        Set<T> list = new ArraySet<>();
        if (!TextUtils.isEmpty(json)) {
            JsonArray array = JsonParser.parseString(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(sGson.fromJson(elem, tClass));
            }
        }
        return list;
    }

    /**
     * Deserialize json to {@link List}
     *
     * @param json json string
     * @param tClass List's elements class
     * @param <T> the type of the List's element
     * @return a empty List if json is empty string or empty array
     */
    public static <T> List<T> jsonToList(String json, Class<T> tClass){
        List<T> list = new ArrayList<>();
        if (!TextUtils.isEmpty(json)) {
            JsonArray array = JsonParser.parseString(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(sGson.fromJson(elem, tClass));
            }
        }
        return list;
    }

    /**
     * Deserialize json to {@link Map}
     * @see #jsonToMap(String, Class, Class)
     *
     * @param json json string
     * @param tClass Map's value class
     * @param <T> the type of the Map's value
     * @return a empty Map if json is empty
     */
    public static <T> Map<String, T> jsonToMap(String json, Class<T> tClass) {
        return jsonToMap(json, String.class, tClass);
    }

    /**
     * Deserialize json to {@link Map}
     *
     * @param json json string
     * @param kClass Map's key class
     * @param vClass Map's value class
     * @param <K>> the type of the Map's key
     * @param <V>> the type of the Map's value
     * @return a empty Map if json is empty
     */
    public static <K, V> Map<K, V> jsonToMap(String json, Class<K> kClass, Class<V> vClass) {
        if (TextUtils.isEmpty(json)) {
            return new LinkedTreeMap<>();
        }
        Type empMapType = new TypeToken<LinkedTreeMap<K, V>>() {}.getType();
        return sGson.fromJson(json, empMapType);
    }

    /**
     * Pretty a json string
     *
     * @param jsonStr raw json string
     * @return a prettied json string
     */
    public static String prettyJson(String jsonStr){
        JsonElement je = JsonParser.parseString(jsonStr);
        return sPrettyGson.toJson(je);
    }

}
