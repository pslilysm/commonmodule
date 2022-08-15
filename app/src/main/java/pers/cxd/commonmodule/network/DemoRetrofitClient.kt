package pers.cxd.commonmodule.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pers.cxd.commonmodule.BuildConfig
import pers.cxd.corelibrary.network.AbstractRetrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object DemoRetrofitClient : AbstractRetrofit() {

    override fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.20/")
            .client(createOkHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(5, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                private val TAG = "CXD-DemoRetrofitClient"
                override fun log(s: String) {
                    Log.d(TAG, s)
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }

}