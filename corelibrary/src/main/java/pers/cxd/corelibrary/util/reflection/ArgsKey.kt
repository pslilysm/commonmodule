package pers.cxd.corelibrary.util.reflection

import java.util.*

class ArgsKey private constructor(args: Array<out Any>) {
    var args: Array<out Any>?
    private var next: ArgsKey? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val argsKey = other as ArgsKey
        return Arrays.equals(args, argsKey.args)
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(args)
    }

    fun recycle() {
        args = null
        synchronized(sPoolLock) {
            if (sMaxPoolSize > sPoolSize) {
                next = sPool
                sPool = this
                sPoolSize++
            }
        }
    }

    companion object {
        private const val sMaxPoolSize = 10
        private val sPoolLock = Any()
        private var sPool: ArgsKey? = null
        private var sPoolSize = 0
        fun obtain(vararg args: Any): ArgsKey {
            synchronized(sPoolLock) {
                if (sPool != null) {
                    val argsKey = sPool
                    sPool = argsKey!!.next
                    argsKey.next = null
                    argsKey.args = args
                    sPoolSize--
                    return argsKey
                }
            }
            return ArgsKey(args)
        }
    }

    init {
        this.args = args
    }
}