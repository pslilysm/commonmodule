package pers.cxd.corelibrary.util

/**
 * Miscellaneous `Collection` and `Map` and `Array` utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object DataStructureUtil {
    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun isEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }

    fun haveElement(collection: Collection<*>?): Boolean {
        return !isEmpty(collection)
    }

    fun haveElement(map: Map<*, *>?): Boolean {
        return map != null && !map.isEmpty()
    }

    fun haveElement(arr: BooleanArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun haveElement(arr: ByteArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun haveElement(arr: CharArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun haveElement(arr: ShortArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun haveElement(arr: IntArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun haveElement(arr: FloatArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun haveElement(arr: LongArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun haveElement(arr: DoubleArray?): Boolean {
        return arr != null && arr.isNotEmpty()
    }

    fun <T> haveElement(arr: Array<T>?): Boolean {
        return arr != null && arr.isNotEmpty()
    }
}