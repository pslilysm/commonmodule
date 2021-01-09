package pers.cxd.commonmodule.activities.splash;

import io.reactivex.rxjava3.core.Observable;
import pers.cxd.commonmodule.presenters.RxPresenter;

interface SplashContract {

    interface View {
        void onSomeDataGotten(Object data);
    }

    interface Model {
        boolean someDataModelAvailable();
        Observable<Object> someDataModel(String arg1, String arg2);
        void clearSomeDataModel();
        Observable<Void> registerModel(String accountName, String password);
    }

    abstract class Presenter extends RxPresenter<View, Model> {
        abstract void getSomeData(String arg1, String arg2);
        abstract void register(String accountName, String password);
    }

}
