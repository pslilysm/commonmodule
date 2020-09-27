package pers.cxd.commonmodule.activities.splash;

import io.reactivex.rxjava3.core.Observable;
import pers.cxd.mvplibrary.BaseModel;
import pers.cxd.mvplibrary.BasePresenter;
import pers.cxd.mvplibrary.BaseView;

interface SplashContract {

    interface View extends BaseView {
        void onSomeDataGotten(Object data);
    }

    interface Model extends BaseModel{
        Observable<Object> getSomeDataModel(String arg1, String arg2);
    }

    abstract class Presenter extends BasePresenter<SplashContract.View, SplashContract.Model>{
        abstract void getSomeData(String arg1, String arg2);
    }

}
