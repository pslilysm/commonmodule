package pers.cxd.rxlibrary;

import java.lang.reflect.ParameterizedType;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public abstract class HttpClient<I> {

    private I mApi;

    public I getApi(){
        return mApi;
    }

    protected abstract String getBaseUrl();
    protected abstract Converter.Factory[] getConvertFactories();
    protected abstract OkHttpClient createHttpClient();

    protected HttpClient(){
        createRetrofitClient();
    }

    private void createRetrofitClient(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(createHttpClient())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        for (Converter.Factory factory : getConvertFactories()){
            builder.addConverterFactory(factory);
        }
        mApi = builder.build().create((Class<I>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
