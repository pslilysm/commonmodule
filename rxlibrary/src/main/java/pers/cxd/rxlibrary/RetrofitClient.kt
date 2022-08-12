package pers.cxd.rxlibrary

import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap

/**
 * This class simplified the step when build [Retrofit]
 *
 * @author pslilysm
 * @since 1.0.0
 */
abstract class RetrofitClient protected constructor() {
    private val mApiMap: MutableMap<Class<*>, Any> = ConcurrentHashMap()
    protected val mRetrofitClient: Retrofit

    /**
     * Provide api service by `apiClass`
     *
     * @param apiClass the class of api service
     * @param <I>      the type of `apiClass`
     * @return A created or cached api service by Retrofit
    </I> */
    fun <I> getApi(apiClass: Class<I>): I {
        return mApiMap.computeIfAbsent(apiClass) { aClass: Class<*>? ->
            mRetrofitClient.create(aClass)
        } as I
    }

    /**
     * Built your own retrofit
     */
    protected abstract fun buildRetrofit(): Retrofit

    init {
        mRetrofitClient = buildRetrofit()
    }
}