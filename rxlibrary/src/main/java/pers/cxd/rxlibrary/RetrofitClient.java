package pers.cxd.rxlibrary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

public abstract class RetrofitClient {

    protected Retrofit mRetrofitClient;
    private final Map<Class<?>, Object> mApiMap = new ConcurrentHashMap<>();

    protected RetrofitClient(){
        mRetrofitClient = createRetrofitClient();
    }

    public <I> I getApi(Class<I> apiClass) {
        return (I) mApiMap.computeIfAbsent(apiClass, aClass -> mRetrofitClient.create(aClass));
    }

    protected abstract String getBaseUrl();
    protected abstract CallAdapter.Factory[] getCallAdapterFactories();
    protected abstract Converter.Factory[] getConvertFactories();
    protected abstract OkHttpClient createOkHttpClient();

    protected Retrofit createRetrofitClient(){
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
