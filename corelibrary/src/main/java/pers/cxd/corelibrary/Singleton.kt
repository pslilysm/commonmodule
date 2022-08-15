package pers.cxd.corelibrary

/**
 * Lazy load singleton pattern
 *
 * @param <T> the type of the singleton instance
 * @author pslilysm
 * @since 1.0.0
 */
abstract class Singleton<T> {
    @Volatile
    var instance: T? = null
    protected abstract fun create(): T
    fun get(): T {
        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    instance = create()
                }
            }
        }
        return instance!!
    }
}