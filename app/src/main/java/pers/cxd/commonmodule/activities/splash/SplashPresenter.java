package pers.cxd.commonmodule.activities.splash;

import io.reactivex.rxjava3.annotations.NonNull;
import pers.cxd.rxlibrary.BaseObserverImpl;
import pers.cxd.rxlibrary.RxUtil;

class SplashPresenter extends SplashContract.Presenter{

    @Override
    void getSomeData(String arg1, String arg2) {
        if (mModel.someDataModelAvailable()){
            RxUtil.execute(mModel.someDataModel(arg1, arg2), new BaseObserverImpl<Object>(mSubscription) {
                @Override
                public void onNext(@io.reactivex.rxjava3.annotations.NonNull Object o) {
                    if (notDetach()){
                        mView.onSomeDataGotten(o);
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                    super.onComplete();
                    if (notDetach()){
                        mModel.clearSomeDataModel();
                    }
                }
            }, RxUtil.Transformers.IOToMain());
        }
    }

    @Override
    void register(String accountName, String password) {
        RxUtil.execute(mModel.registerModel(accountName, password), new BaseObserverImpl<Void>(mSubscription) {
            @Override
            public void onNext(@NonNull Void aVoid) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        }, RxUtil.Transformers.IOToMain());
    }
}
