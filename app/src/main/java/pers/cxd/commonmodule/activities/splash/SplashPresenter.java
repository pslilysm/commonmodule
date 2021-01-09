package pers.cxd.commonmodule.activities.splash;

import android.util.Log;

import androidx.annotation.NonNull;

import pers.cxd.rxlibrary.HttpCallback;
import pers.cxd.rxlibrary.RxCallbackImpl;
import pers.cxd.rxlibrary.RxUtil;
import retrofit2.HttpException;

class SplashPresenter extends SplashContract.Presenter{

    @Override
    void getSomeData(String arg1, String arg2) {
        if (mModel.someDataModelAvailable()){
            RxCallbackImpl<Object> callbackImpl = new RxCallbackImpl<Object>(mSubscription) {
                @Override
                public void onSuccess(@NonNull Object o) {
                    if (notDetach()){
                        mView.onSomeDataGotten(o);
                    }
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                    if (notDetach()){
                        mModel.clearSomeDataModel();
                    }
                }
            };
            RxUtil.execute(new HttpCallback<Object>(callbackImpl) {
                @Override
                public void onNetworkError(Throwable e, String errMsg) {

                }
            }, mModel.someDataModel(arg1, arg2), RxUtil.Transformers.IOToMain());
        }
    }

    @Override
    void register(String accountName, String password) {
        RxUtil.execute(new HttpCallback<Void>(new RxCallbackImpl<Void>(mSubscription) {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Void aVoid) {

            }
        }) {
            @Override
            public void onNetworkError(Throwable e, String errMsg) {
                if (e instanceof HttpException){
                    Log.e(TAG, "onNetworkError: " + ((HttpException) e).code());
                }
                Log.e(TAG, "onNetworkError: " + errMsg, e);
            }
        }, mModel.registerModel(accountName, password), RxUtil.Transformers.IOToMain());
    }
}
