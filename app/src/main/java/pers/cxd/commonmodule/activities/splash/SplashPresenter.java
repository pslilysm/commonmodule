package pers.cxd.commonmodule.activities.splash;

import androidx.annotation.NonNull;
import pers.cxd.commonmodule.network.DemoHttpClient;

class SplashPresenter extends SplashContract.Presenter{

    @Override
    void getSomeData(String arg1, String arg2) {
        DemoHttpClient.get().doReq(new BaseHttpCallbackImpl<Object>() {
            @Override
            public void onSuccess(@NonNull Object o) {
                if (mView != null){
                    mView.onSomeDataGotten(o);
                }
            }

            @Override
            public void onNetworkError(Throwable e, String errMsg) {
                // handle error with yourself logic
            }
        }, mModel.someDataModel(arg1, arg2));
    }

}
