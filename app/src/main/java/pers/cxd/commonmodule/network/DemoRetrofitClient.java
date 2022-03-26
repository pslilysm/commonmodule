package pers.cxd.commonmodule.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pers.cxd.commonmodule.BuildConfig;
import pers.cxd.corelibrary.SingletonFactory;
import pers.cxd.rxlibrary.RetrofitClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DemoRetrofitClient extends RetrofitClient {

    public static DemoRetrofitClient getInstance() {
        return SingletonFactory.findOrCreate(DemoRetrofitClient.class);
    }

    @Override
    protected String getBaseUrl() {
        return "http://192.168.0.133/";
    }

    @Override
    protected CallAdapter.Factory[] getCallAdapterFactories() {
        return new CallAdapter.Factory[]{RxJava3CallAdapterFactory.createSynchronous()};
    }

    @Override
    protected Converter.Factory[] getConvertFactories() {
        return new Converter.Factory[]{ScalarsConverterFactory.create(), GsonConverterFactory.create()};
    }

    @Override
    protected OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                final String TAG = DemoRetrofitClient.class.getSimpleName();

                @Override
                public void log(@NonNull String s) {
                    Log.d(TAG, s);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }
}
