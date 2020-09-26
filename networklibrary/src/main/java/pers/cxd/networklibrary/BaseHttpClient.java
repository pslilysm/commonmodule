package pers.cxd.networklibrary;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseHttpClient<I> {

    private I apiInterface;

    public I getApiInterface(){
        return apiInterface;
    }

    abstract Class<?> getApiInterfaceClass();
    abstract NetworkConfig getNetworkConfig();

    public void initHttpClient(){
        apiInterface = (I) new Retrofit.Builder()
                .baseUrl(getNetworkConfig().getBaseUrl())
                .client(createHttpClientBuilder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(getApiInterfaceClass());
    }

    private OkHttpClient.Builder createHttpClientBuilder(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        if (BuildConfig.DEBUG){
            builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                String TAG = BaseHttpClient.class.getSimpleName();
                @Override
                public void log(@NotNull String s) {
                    Log.d(TAG, s);
                }
            }));
        }
        return builder;
    }

    public <D> void doReq(Observable<D> observable, final HttpCallback<D> callback, Scheduler observeScheduler){
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(observeScheduler)
                .subscribe(new Observer<D>() {
                    Disposable disposable;
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                        callback.addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull D d) {
                        if (d == null){
                            callback.onNetworkFailure(new NullPointerException("server return null data"),
                                    "server return null data");
                        }else {
                            callback.onSuccess(d);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String errorClassName = e.getClass().getName();
                        if (e instanceof HttpException
                                || errorClassName.startsWith("java.net")
                                || errorClassName.startsWith("javax.net")){
                            String errMsg = null;
                            if (e instanceof  HttpException){
                                // read the errMsg from server
                                try {
                                    errMsg = ((HttpException) e).response().errorBody().string();
                                } catch (Exception ex) {
                                    // Empty
                                }
                            }
                            if (errMsg == null){
                                errMsg = e.getMessage();
                            }
                            callback.onNetworkFailure(e, errMsg);
                            dispose();
                        }else {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        dispose();
                    }

                    private void dispose(){
                        if (disposable != null && !disposable.isDisposed()){
                            disposable.dispose();
                            disposable = null;
                        }
                    }

                });
    }

}
