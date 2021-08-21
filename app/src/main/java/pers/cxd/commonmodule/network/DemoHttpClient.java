package pers.cxd.commonmodule.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pers.cxd.commonmodule.BuildConfig;
import pers.cxd.corelibrary.SingletonFactory;
import pers.cxd.rxlibrary.HttpClient;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

public class DemoHttpClient extends HttpClient {

    public static DemoHttpClient getInstance(){
        return SingletonFactory.findOrCreate(DemoHttpClient.class);
    }

    @Override
    protected String getBaseUrl() {
        return "http://192.168.0.133/";
    }

    @Override
    protected Converter.Factory[] getConvertFactories() {
        return new Converter.Factory[]{GsonConverterFactory.create()};
    }

    @Override
    protected OkHttpClient createHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG){
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                final String TAG = DemoHttpClient.class.getSimpleName();
                @Override
                public void log(@NonNull String s) {
                    Log.d(TAG, s);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }
}
