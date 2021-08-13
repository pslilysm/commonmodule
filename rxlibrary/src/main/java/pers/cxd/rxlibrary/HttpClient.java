package pers.cxd.rxlibrary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public abstract class HttpClient {

    private final Retrofit mRetrofitClient;
    private final Map<Class<?>, Object> mApiMap = new ConcurrentHashMap<>();

    protected HttpClient(){
        mRetrofitClient = createRetrofitClient();
    }

    public <I> I getApi(Class<I> apiClass) {
        return (I) mApiMap.computeIfAbsent(apiClass, new Function<Class<?>, Object>() {
            @Override
            public Object apply(Class<?> aClass) {
                return mRetrofitClient.create(aClass);
            }
        });
    }

    protected abstract String getBaseUrl();
    protected abstract Converter.Factory[] getConvertFactories();
    protected abstract OkHttpClient createHttpClient();

    private Retrofit createRetrofitClient(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(createHttpClient())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        for (Converter.Factory factory : getConvertFactories()){
            builder.addConverterFactory(factory);
        }
        return builder.build();
    }

}
