package pers.cxd.commonmodule.activities.splash;

import androidx.annotation.NonNull;
import pers.cxd.commonmodule.network.DemoHttpClient;

class SplashPresenter extends SplashContract.Presenter{

    @Override
    void getSomeData(String arg1, String arg2) {
        DemoHttpClient.get().doReq(mModel.getSomeDataModel(arg1, arg2), new BaseHttpCallback<Object>() {
            @Override
            public void onSuccess(@NonNull Object o) {
                if (mView != null){
                    mView.onSomeDataGotten(o);
                }
            }

            @Override
            public void onNetworkError(Throwable e, String errMsg) {
                // do yourself logic to handle error
            }
        });
    }

}
