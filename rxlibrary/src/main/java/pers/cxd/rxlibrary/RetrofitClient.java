package pers.cxd.rxlibrary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * This class simplified the step when build {@link Retrofit}
 *
 * @author pslilysm
 * @since 1.0.0
 */
public abstract class RetrofitClient {

    private final Map<Class<?>, Object> mApiMap = new ConcurrentHashMap<>();
    protected Retrofit mRetrofitClient;

    protected RetrofitClient() {
        buildRetrofit();
    }

    /**
     * Provide api service by {@code apiClass}
     *
     * @param apiClass the class of api service
     * @param <I>      the type of {@code apiClass}
     * @return A created or cached api service by Retrofit
     */
    public <I> I getApi(Class<I> apiClass) {
        return (I) mApiMap.computeIfAbsent(apiClass, aClass -> mRetrofitClient.create(aClass));
    }

    protected abstract String getBaseUrl();

    protected abstract CallAdapter.Factory[] getCallAdapterFactories();

    protected abstract Converter.Factory[] getConvertFactories();

    protected abstract OkHttpClient createOkHttpClient();

    /**
     * Subclass calling this method at the appropriate time
     */
    protected void buildRetrofit() {
        mRetrofitClient = createRetrofitClient();
    }

    protected Retrofit createRetrofitClient() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(createOkHttpClient());
        for (CallAdapter.Factory callAdapterFactory : getCallAdapterFactories()) {
            builder.addCallAdapterFactory(callAdapterFactory);
        }
        for (Converter.Factory convertFactory : getConvertFactories()) {
            builder.addConverterFactory(convertFactory);
        }
        return builder.build();
    }

}
