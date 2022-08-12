package pers.cxd.corelibrary.util.reflection

import java.util.*

class ConstructorKey private constructor(
    var clazz: Class<*>?,
    var parameterTypes: Array<out Class<*>?>
) {
    private var inUse = false
    private var next: ConstructorKey? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as ConstructorKey
        return clazz == that.clazz &&
                parameterTypes.contentEquals(that.parameterTypes)
    }

    override fun hashCode(): Int {
        var result = Objects.hash(clazz)
        result = 31 * result + parameterTypes.contentHashCode()
        return result
    }

    fun markInUse() {
        inUse = true
    }

    fun recycle() {
        check(!inUse) { "$this is in use, can't recycle" }
        clazz = null
        parameterTypes = emptyArray()
        synchronized(ConstructorKey::class.java) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool
                sPool = this
                sPoolSize++
            }
        }
    }

    companion object {
        private const val MAX_POOL_SIZE = 10
        private var sPool: ConstructorKey? = null
        private var sPoolSize = 0
        fun obtain(clazz: Class<*>?, vararg parameterTypes: Class<*>?): ConstructorKey {
            synchronized(ConstructorKey::class.java) {
                if (sPool != null) {
                    val m = sPool
                    sPool = m!!.next
                    m.next = null
                    sPoolSize--
                    m.clazz = clazz
                    m.parameterTypes = parameterTypes
                    return m
                }
            }
            return ConstructorKey(clazz, parameterTypes)
        }
    }
}