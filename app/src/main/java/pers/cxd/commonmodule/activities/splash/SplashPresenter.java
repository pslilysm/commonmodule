package pers.cxd.commonmodule.activities.splash;

import android.util.Log;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;
import pers.cxd.rxlibrary.HttpCallback;
import pers.cxd.rxlibrary.RxUtil;

class SplashPresenter extends SplashContract.Presenter{

    @Override
    void getSomeData(String arg1, String arg2) {
        if (mModel.someDataModelAvailable()){
            RxUtil.execute(new AddDisposableCallback<>(new HttpCallback<Object>() {
                @Override
                public void onSuccess(@NonNull Object o) {
                    if (notDetach()){
                        mView.onSomeDataGotten(o);
                    }
                }

                @Override
                public void onComplete() {
                    if (notDetach()){
                        mModel.clearSomeDataModel();
                    }
                }

                @Override
                protected void onNetworkError(Throwable e, String errMsg) {
                    Log.d(TAG, "onNetworkError() called with: e = [" + e + "], errMsg = [" + errMsg + "]");
                }
            }), mModel.someDataModel(arg1, arg2), RxUtil.getIOToMainTransformer());
        }
    }

}
