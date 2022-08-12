package pers.cxd.corelibrary.util.reflection

import java.util.*

class FieldKey private constructor(var clazz: Class<*>?, var filedName: String?) {
    private var inUse = false
    private var next: FieldKey? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val fieldKey = o as FieldKey
        return clazz == fieldKey.clazz &&
                filedName == fieldKey.filedName
    }

    override fun hashCode(): Int {
        return Objects.hash(clazz, filedName)
    }

    fun markInUse() {
        inUse = true
    }

    fun recycle() {
        check(!inUse) { "$this is in use, can't recycle" }
        clazz = null
        filedName = null
        synchronized(FieldKey::class.java) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool
                sPool = this
                sPoolSize++
            }
        }
    }

    companion object {
        private const val MAX_POOL_SIZE = 10
        private var sPool: FieldKey? = null
        private var sPoolSize = 0
        fun obtain(clazz: Class<*>?, filedName: String?): FieldKey {
            synchronized(FieldKey::class.java) {
                if (sPool != null) {
                    val fk = sPool
                    sPool = fk!!.next
                    fk.next = null
                    sPoolSize--
                    fk.clazz = clazz
                    fk.filedName = filedName
                    return fk
                }
            }
            return FieldKey(clazz, filedName)
        }
    }
}