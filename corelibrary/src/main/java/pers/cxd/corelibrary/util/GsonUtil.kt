package pers.cxd.corelibrary.util

import android.text.TextUtils
import androidx.collection.ArraySet
import com.google.gson.*
import com.google.gson.internal.LinkedTreeMap
import pers.cxd.corelibrary.annotation.GsonExclude
import pers.cxd.corelibrary.util.reflection.ReflectionUtil

/**
 * Miscellaneous [Gson] utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object GsonUtil {
    private val sPrettyGson: Gson
    private val sGson: Gson
    /**
     * Serialize object to json
     *
     * @param obj    to serialize
     * @param pretty ture we'll returned a pretty json string
     * @return json string
     */
    /**
     * @return a json string without pretty
     * @see .objToJson
     */
    @JvmOverloads
    fun objToJson(obj: Any?, pretty: Boolean = false): String {
        return if (pretty) sPrettyGson.toJson(obj) else sGson.toJson(obj)
    }

    /**
     * Deserialize json to object
     *
     * @param json   json string
     * @param tClass class of object will returned
     * @param <T>    the type of the object returned
     * @return a object deserialize of json string
    </T> */
    fun <T> jsonToObject(json: String?, tClass: Class<T>?): T {
        return sGson.fromJson(json, tClass)
    }

    /**
     * Deserialize json to [Set]
     *
     * @param json   json string
     * @param tClass Set's elements class
     * @param <T>    the type of the Set's element
     * @return a empty Set if json is empty string or empty array
    </T> */
    fun <T> jsonToSet(json: String?, tClass: Class<T>?): Set<T> {
        val list: MutableSet<T> = ArraySet()
        if (!TextUtils.isEmpty(json)) {
            val array = JsonParser.parseString(json).asJsonArray
            for (elem in array) {
                list.add(sGson.fromJson(elem, tClass))
            }
        }
        return list
    }

    /**
     * Deserialize json to [List]
     *
     * @param json   json string
     * @param tClass List's elements class
     * @param <T>    the type of the List's element
     * @return a empty List if json is empty string or empty array
    </T> */
    fun <T> jsonToList(json: String?, tClass: Class<T>?): List<T> {
        val list: MutableList<T> = ArrayList()
        if (!TextUtils.isEmpty(json)) {
            val array = JsonParser.parseString(json).asJsonArray
            for (elem in array) {
                list.add(sGson.fromJson(elem, tClass))
            }
        }
        return list
    }

    /**
     * Deserialize json to [Map]
     *
     * @param json json string
     * @param <V>> the type of the Map's value
     * @return a empty Map if json is empty
    </V> */
    @kotlin.jvm.JvmStatic
    fun <V> jsonToMap(json: String?, vClass: Class<V>?): Map<String, V> {
        val result: MutableMap<String, V> = LinkedTreeMap()
        if (TextUtils.isEmpty(json)) {
            return result
        }
        val jsonObject = sGson.fromJson(json, JsonObject::class.java)
        try {
            val members = ReflectionUtil.getFieldValue<LinkedTreeMap<String, JsonElement>>(
                jsonObject,
                "members"
            )
            members.forEach { (s: String, jsonElement: JsonElement?) ->
                result[s] = sGson.fromJson(jsonElement, vClass)
            }
        } catch (e: ReflectiveOperationException) {
            // never happen
            throw RuntimeException()
        }
        return result
    }

    /**
     * Pretty a json string
     *
     * @param jsonStr raw json string
     * @return a prettied json string
     */
    fun prettyJson(jsonStr: String?): String {
        val je = JsonParser.parseString(jsonStr)
        return sPrettyGson.toJson(je)
    }

    init {
        val strategy: ExclusionStrategy = object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.getAnnotation(GsonExclude::class.java) != null
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return clazz.getAnnotation(GsonExclude::class.java) != null
            }
        }
        sPrettyGson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
            .setExclusionStrategies(strategy).create()
        sGson = GsonBuilder().disableHtmlEscaping()
            .setExclusionStrategies(strategy).create()
    }
}