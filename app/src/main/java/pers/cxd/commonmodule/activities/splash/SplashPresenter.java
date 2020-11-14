package pers.cxd.commonmodule.activities.splash;

import androidx.annotation.NonNull;

import pers.cxd.rxlibrary.AddDisposableCallback;
import pers.cxd.rxlibrary.HttpCallback;
import pers.cxd.rxlibrary.RxCallback;
import pers.cxd.rxlibrary.RxUtil;

class SplashPresenter extends SplashContract.Presenter{

    @Override
    void getSomeData(String arg1, String arg2) {
        if (mModel.someDataModelAvailable()){
            RxUtil.execute(new AddDisposableCallback<>(new HttpCallback<Object>(new RxCallback<Object>() {
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
            }) {
                @Override
                public void onNetworkError(Throwable e, String errMsg) {

                }
            }, mSubscription), mModel.someDataModel(arg1, arg2), RxUtil.getIOToMainTransformer());
        }
    }

}
