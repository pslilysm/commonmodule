package pers.cxd.commonmodule.activities.splash

import io.reactivex.rxjava3.core.Observable
import pers.cxd.commonmodule.presenters.RxPresenter
import pers.cxd.corelibrary.base.BaseView

interface SplashContract {
    interface View : BaseView {
        fun onSomeDataGotten(data: Any)
    }

    interface Model {
        fun someDataModelAvailable(): Boolean
        fun someDataModel(arg1: String, arg2: String): Observable<Any>
        fun clearSomeDataModel()
        fun registerModel(accountName: String, password: String): Observable<Void>
    }

    abstract class Presenter : RxPresenter<View, Model>() {
        abstract fun getSomeData(arg1: String, arg2: String)
        abstract fun register(accountName: String, password: String)
    }
}