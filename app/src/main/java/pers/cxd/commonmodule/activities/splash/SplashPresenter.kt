package pers.cxd.commonmodule.activities.splash

import pers.cxd.rxlibrary.BaseObserverImpl
import pers.cxd.rxlibrary.RxUtil

internal object SplashPresenter : SplashContract.Presenter() {
    override fun getSomeData(arg1: String, arg2: String) {
        if (mModel!!.someDataModelAvailable()) {
            RxUtil.execute(
                mModel!!.someDataModel(arg1, arg2),
                object : BaseObserverImpl<Any>(mSubscription) {
                    override fun onNext(o: Any) {
                        if (notDetach()) {
                            mView!!.onSomeDataGotten(o)
                        }
                    }

                    override fun onError(e: Throwable) {}
                    override fun onComplete() {
                        super.onComplete()
                        if (notDetach()) {
                            mModel!!.clearSomeDataModel()
                        }
                    }
                },
                RxUtil.Transformers.IOToMain()
            )
        }
    }

    override fun register(accountName: String, password: String) {
        RxUtil.execute(
            mModel!!.registerModel(accountName, password),
            object : BaseObserverImpl<Void>(mSubscription) {
                override fun onNext(t: Void) { }
                override fun onError(e: Throwable) {}
            },
            RxUtil.Transformers.IOToMain()
        )
    }
}