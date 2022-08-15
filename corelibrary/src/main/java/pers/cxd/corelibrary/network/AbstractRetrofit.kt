package pers.cxd.corelibrary.network

import pers.cxd.corelibrary.util.function.SuspendFunction
import retrofit2.Retrofit
import java.util.concurrent.ConcurrentHashMap

/**
 * AbstractRetrofit is an extension of Retrofit, providing API service caching capabilities and basic data fetch capabilities
 *
 * @author pslilysm
 * @since 1.0.0
 */
abstract class AbstractRetrofit protected constructor() {
    private val mApiMap: MutableMap<Class<*>, Any> = ConcurrentHashMap()
    protected val mRetrofitClient: Retrofit

    /**
     * Provide api service by `apiClass`
     *
     * @param apiClass the class of api service
     * @param <I>      the type of `apiClass`
     * @return A created or cached api service by Retrofit
     */
    fun <I> getApi(apiClass: Class<I>): I {
        return mApiMap.computeIfAbsent(apiClass) { aClass: Class<*>? ->
            mRetrofitClient.create(aClass)
        } as I
    }

    suspend fun <Data> fetchData(function: SuspendFunction<Data>): Result<Data> {
        return try {
            Result.success(function.invoke())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Built your own retrofit
     */
    protected abstract fun buildRetrofit(): Retrofit

    init {
        mRetrofitClient = buildRetrofit()
    }
}