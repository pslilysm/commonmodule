package pers.cxd.corelibrary.util;

import android.text.TextUtils;

import androidx.collection.ArraySet;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pers.cxd.corelibrary.annotation.GsonExclude;
import pers.cxd.corelibrary.util.reflection.ReflectionUtil;

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
                return f.getAnnotation(GsonExclude.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return clazz.getAnnotation(GsonExclude.class) != null;
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
     *
     * @param json json string
     * @param <V>> the type of the Map's value
     * @return a empty Map if json is empty
     */
    public static <V> Map<String, V> jsonToMap(String json, Class<V> vClass) {
        Map<String, V> result = new LinkedTreeMap<>();
        if (TextUtils.isEmpty(json)) {
            return result;
        }
        JsonObject jsonObject = sGson.fromJson(json, JsonObject.class);
        try {
            LinkedTreeMap<String, JsonElement> members = ReflectionUtil.getFieldValue(jsonObject, "members");
            members.forEach((s, jsonElement) -> result.put(s, sGson.fromJson(jsonElement, vClass)));
        } catch (ReflectiveOperationException e) {
            // never happen
            throw new RuntimeException();
        }
        return result;
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
