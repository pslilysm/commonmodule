package pers.cxd.networklibrary;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public abstract class HttpClient<I> {

    private I mApiInterface;

    public I getApiInterface(){
        return mApiInterface;
    }

    protected abstract Class<?> getApiInterfaceClass();
    protected abstract String getBaseUrl();
    protected abstract Converter.Factory[] getConvertFactories();
    protected abstract OkHttpClient createHttpClient();

    public void createRetrofitClient(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(createHttpClient())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        for (Converter.Factory factory : getConvertFactories()){
            builder.addConverterFactory(factory);
        }
        mApiInterface = (I) builder.build().create(getApiInterfaceClass());
    }

    public <D> void doReq(final HttpCallback<D> callback, Observable<D> observable){
        doReq(callback, observable, AndroidSchedulers.mainThread());
    }

    public <D> void doReq(final HttpCallback<D> callback, Observable<D> observable, Scheduler observeScheduler){
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
                        callback.onSuccess(d);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String errorClassName = e.getClass().getName();
                        if (e instanceof HttpException
                                || errorClassName.startsWith("java.net")
                                || errorClassName.startsWith("javax.net")){
                            String errMsg = null;
                            if (e instanceof HttpException){
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
                            callback.onNetworkError(e, errMsg);
                        }else {
                            if (!callback.handleAnotherError(e)){
                                // in release version, we normally use bugly or another sdk to report this error;
                                // so always make your handleAnotherError return true;
                                throw new RuntimeException(e);
                            }
                        }
                        dispose();
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
