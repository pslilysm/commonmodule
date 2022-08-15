package pers.cxd.corelibrary.util

/**
 * Miscellaneous `Collection` and `Map` and `Array` utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object DataStructureUtil {
    @kotlin.jvm.JvmStatic
    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    @kotlin.jvm.JvmStatic
    fun isEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(collection: Collection<*>?): Boolean {
        return !isEmpty(collection)
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(map: Map<*, *>?): Boolean {
        return map != null && map.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: BooleanArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: ByteArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: CharArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: ShortArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: IntArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: FloatArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: LongArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun haveElement(arr: DoubleArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
    @kotlin.jvm.JvmStatic
    fun <T> haveElement(arr: Array<T>?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
}