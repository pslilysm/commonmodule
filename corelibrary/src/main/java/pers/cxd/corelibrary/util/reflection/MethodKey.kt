package pers.cxd.corelibrary.util.reflection

import java.util.*

class MethodKey private constructor(
    var clazz: Class<*>?,
    var methodName: String?,
    var parameterTypes: Array<out Class<*>?>
) {
    private var inUse = false
    private var next: MethodKey? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val methodKey = other as MethodKey
        return clazz == methodKey.clazz &&
                methodName == methodKey.methodName &&
                parameterTypes.contentEquals(methodKey.parameterTypes)
    }

    override fun hashCode(): Int {
        var result = Objects.hash(clazz, methodName)
        result = 31 * result + parameterTypes.contentHashCode()
        return result
    }

    fun markInUse() {
        inUse = true
    }

    fun recycle() {
        check(!inUse) { "$this is in use, can't recycle" }
        clazz = null
        methodName = null
        parameterTypes = emptyArray()
        synchronized(MethodKey::class.java) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool
                sPool = this
                sPoolSize++
            }
        }
    }

    companion object {
        private const val MAX_POOL_SIZE = 10
        private var sPool: MethodKey? = null
        private var sPoolSize = 0
        fun obtain(
            clazz: Class<*>,
            methodName: String,
            vararg parameterTypes: Class<*>?
        ): MethodKey {
            synchronized(MethodKey::class.java) {
                if (sPool != null) {
                    val mk = sPool
                    sPool = mk!!.next
                    mk.next = null
                    sPoolSize--
                    mk.clazz = clazz
                    mk.methodName = methodName
                    mk.parameterTypes = parameterTypes
                    return mk
                }
            }
            return MethodKey(clazz, methodName, parameterTypes)
        }
    }
}