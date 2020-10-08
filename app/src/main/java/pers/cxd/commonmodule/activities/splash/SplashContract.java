package pers.cxd.commonmodule.activities.splash;

import io.reactivex.rxjava3.core.Observable;
import pers.cxd.mvplibrary.MvpModel;
import pers.cxd.mvplibrary.MvpPresenter;
import pers.cxd.mvplibrary.MvpView;

interface SplashContract {

    interface View extends MvpView {
        void onSomeDataGotten(Object data);
    }

    interface Model extends MvpModel {
        Observable<Object> getSomeDataModel(String arg1, String arg2);
    }

    abstract class Presenter extends MvpPresenter<View, Model> {
        abstract void getSomeData(String arg1, String arg2);
    }

}
