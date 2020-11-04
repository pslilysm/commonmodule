package pers.cxd.commonmodule.activities.splash;

import io.reactivex.rxjava3.core.Observable;
import pers.cxd.commonmodule.network.NetworkPresenter;
import pers.cxd.mvplibrary.MvpModel;
import pers.cxd.mvplibrary.MvpView;

interface SplashContract {

    interface View extends MvpView {
        void onSomeDataGotten(Object data);
    }

    interface Model extends MvpModel {
        boolean someDataModelAvailable();
        Observable<Object> someDataModel(String arg1, String arg2);
        void clearSomeDataModel();
    }

    abstract class Presenter extends NetworkPresenter<View, Model> {
        abstract void getSomeData(String arg1, String arg2);
    }

}
