package pers.cxd.commonmodule.activities.splash;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.disposables.Disposable;
import pers.cxd.commonmodule.network.DemoHttpClient;

class SplashPresenter extends SplashContract.Presenter{

    @Override
    void getSomeData(String arg1, String arg2) {
        if (mModel.someDataModelAvailable()){
            DemoHttpClient.getInstance().doReq(new BaseHttpCallbackImpl<Object>() {
                @Override
                public void onSuccess(@NonNull Object o) {
                    if (notDetach()){
                        mView.onSomeDataGotten(o);
                    }
                }

                @Override
                public void onNetworkError(Throwable e, String errMsg) {
                    // handle error with yourself logic
                }

                @Override
                public void onComplete() {
                    if (notDetach()){
                        mModel.clearSomeDataModel();
                    }
                }
            }, mModel.someDataModel(arg1, arg2));
        }
    }

}
