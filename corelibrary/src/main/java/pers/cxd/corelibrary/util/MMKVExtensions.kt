package pers.cxd.corelibrary.util

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * Extensions for [MMKV]
 *
 * @author pslilysm
 * @since 2.0.0
 * Created on 2022/8/11 18:15
 */
object MMKVExtensions {

    /**
     * encode a non-null value
     *
     * @param key   mapped value
     * @param value mapped key
     */
    fun MMKV.encode(key: String, value: Any?) {
        encode(key, value, null)
    }

    /**
     * encode a null value
     *
     * @param key        mapped value
     * @param valueClass value's class(such as String, Integer, Double}
     */
    fun MMKV.encode(key: String, valueClass: Class<*>?) {
        encode(key, null, valueClass)
    }

    private fun MMKV.encode(key: String, value: Any?, valueClass: Class<*>?) {
        val clazz = value?.javaClass ?: valueClass!!
        if (clazz == Int::class.java) {
            this.encode(key, (value as Int?)!!)
        } else if (clazz == Float::class.java) {
            this.encode(key, (value as Float?)!!)
        } else if (clazz == Double::class.java) {
            this.encode(key, (value as Double?)!!)
        } else if (clazz == Long::class.java) {
            this.encode(key, (value as Long?)!!)
        } else if (clazz == Boolean::class.java) {
            this.encode(key, (value as Boolean?)!!)
        } else if (clazz == String::class.java) {
            this.encode(key, value as String?)
        } else if (MutableSet::class.java.isAssignableFrom(clazz)) {
            this.encode(key, value as Set<String?>?)
        } else if (clazz == ByteArray::class.java) {
            this.encode(key, value as ByteArray?)
        } else if (Parcelable::class.java.isAssignableFrom(clazz)) {
            this.encode(key, value as Parcelable?)
        } else {
            throw UnsupportedOperationException("$clazz can't encode")
        }
    }

    /**
     * Decode the value mapped by the `key`
     *
     * @param key          mapped value
     * @param defaultValue will be return if not find the key
     * @return saved value by key
     */
    fun <T> MMKV.decode(key: String, defaultValue: T): T {
        return decode<T>(key, defaultValue, null)
    }

    /**
     * Decode the value mapped by the `key`
     *
     * @param key        mapped value
     * @param valueClass the returned value will instance of valueClass
     * @return null if not found the key
     */
    fun <T> MMKV.decode(key: String, valueClass: Class<T?>?): T? {
        return decode(key, null, valueClass)
    }

    private fun <T> MMKV.decode(key: String, defaultValue: T?, valueClass: Class<T>?): T {
        val clazz: Class<*> = if (defaultValue != null) {
            defaultValue!!::class.java
        } else {
            valueClass!!
        }
        return if (clazz == Int::class.java) {
            Integer.valueOf(
                this.decodeInt(
                    key,
                    (if (defaultValue == null) 0 else defaultValue as Int?)!!
                )
            ) as T
        } else (if (clazz == Float::class.java) {
            java.lang.Float.valueOf(
                this.decodeFloat(
                    key,
                    (if (defaultValue == null) 0 else defaultValue as Float?) as Float
                )
            ) as T
        } else if (clazz == Double::class.java) {
            java.lang.Double.valueOf(
                this.decodeDouble(
                    key,
                    (if (defaultValue == null) 0 else defaultValue as Double?) as Double
                )
            ) as T
        } else if (clazz == Long::class.java) {
            java.lang.Long.valueOf(
                this.decodeLong(
                    key,
                    (if (defaultValue == null) 0 else defaultValue as Long?)!!
                )
            ) as T
        } else if (clazz == Boolean::class.java) {
            java.lang.Boolean.valueOf(
                this.decodeBool(
                    key,
                    defaultValue != null && defaultValue as Boolean
                )
            ) as T
        } else if (clazz == String::class.java) {
            this.decodeString(key, defaultValue as String?) as T?
        } else if (MutableSet::class.java.isAssignableFrom(clazz)) {
            this.decodeStringSet(
                key,
                defaultValue as Set<String?>?,
                clazz as Class<out MutableSet<*>?>
            ) as T?
        } else if (clazz == ByteArray::class.java) {
            this.decodeBytes(key, defaultValue as ByteArray?) as T?
        } else {
            throw java.lang.UnsupportedOperationException("$clazz can't decode")
        })!!
    }

    /**
     * Decode [Parcelable] by the key
     *
     * @param key    mapped parcelable value
     * @param tClass value's class
     * @param <T>    the type of the value and must extends [Parcelable]
     * @return a parcelable value by the key
     * @see .decodeParcelable
     */
    fun <T : Parcelable?> MMKV.decodeParcelable(key: String?, tClass: Class<T?>?): T? {
        return decodeParcelable(key, tClass, null)
    }

    /**
     * Decode [Parcelable] by the key
     *
     * @param key          mapped parcelable value
     * @param tClass       value's class
     * @param defaultValue if we didn't find the value by the key, then `defaultValue` returned
     * @param <T>          the type of the value and must extends [Parcelable]
     * @return a parcelable value by the key
     */
    fun <T : Parcelable?> MMKV.decodeParcelable(
        key: String?,
        tClass: Class<T>?,
        defaultValue: T
    ): T? {
        return this.decodeParcelable(key, tClass, defaultValue)
    }

}