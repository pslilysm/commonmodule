package pers.cxd.commonmodule.presenters

import io.reactivex.rxjava3.disposables.CompositeDisposable
import pers.cxd.corelibrary.base.BaseView
import pers.cxd.corelibrary.base.Presenter


open class RxPresenter<V : BaseView, M : Any> : Presenter<V, M>() {
    @JvmField
    protected var mSubscription: CompositeDisposable? = null
    override fun attach(v: V, m: M) {
        super.attach(v, m)
        mSubscription = CompositeDisposable()
    }

    override fun detach() {
        super.detach()
        mSubscription?.dispose()
        mSubscription = null
    }

    public override fun notDetach(): Boolean {
        return super.notDetach() && mSubscription != null
    }
}