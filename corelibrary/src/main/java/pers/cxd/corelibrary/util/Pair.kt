package pers.cxd.corelibrary.util

import pers.cxd.corelibrary.util.function.Recyclable
import java.util.*

/**
 * Container to ease passing around a tuple of two objects. This object provides a sensible
 * implementation of equals(), returning true if equals() is true on each of the contained
 * objects.
 * This object also implements a recyclable interface.
 * When it is reclaimed, if the tuple of two objects can be reclaimed, the tuple will also be reclaimed.
 *
 * @param <F> the type of the first object
 * @param <S> the type of the second object
 * @author pslilysm
 * @since 1.0.0
</S></F> */
class Pair<F, S> private constructor(first: F, second: S) : Recyclable {
    private var first: F?
    private var second: S?
    private var next: Pair<*, *>? = null
    fun first(): F? {
        return first
    }

    fun second(): S? {
        return second
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val pair = o as Pair<*, *>
        return first == pair.first &&
                second == pair.second
    }

    override fun hashCode(): Int {
        return Objects.hash(first, second)
    }

    override fun recycle() {
        recycle(true)
    }

    /**
     * recycle the pair
     *
     * @param recycleIfCan if true will do recycle if key or value instance of Recyclable
     */
    fun recycle(recycleIfCan: Boolean) {
        if (recycleIfCan) {
            if (first is Recyclable) {
                (first as Recyclable).recycle()
            }
            if (second is Recyclable) {
                (second as Recyclable).recycle()
            }
        }
        first = null
        second = null
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
        private var sPool: Pair<*, *>? = null
        private var sPoolSize = 0
        fun <F, S> obtain(first: F, second: S): Pair<F, S> {
            synchronized(sPoolLock) {
                if (sPool != null) {
                    val pair = sPool as Pair<F, S>?
                    sPool = pair!!.next
                    pair.next = null
                    pair.first = first
                    pair.second = second
                    sPoolSize--
                    return pair
                }
            }
            return Pair(first, second)
        }
    }

    init {
        this.first = first
        this.second = second
    }
}