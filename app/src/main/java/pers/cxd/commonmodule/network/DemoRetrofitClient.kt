package pers.cxd.commonmodule.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pers.cxd.commonmodule.BuildConfig
import pers.cxd.rxlibrary.RetrofitClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object DemoRetrofitClient : RetrofitClient() {

    override fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.133/")
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(20, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                val TAG = DemoRetrofitClient::class.java.simpleName
                override fun log(s: String) {
                    Log.d(TAG, s)
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }

}