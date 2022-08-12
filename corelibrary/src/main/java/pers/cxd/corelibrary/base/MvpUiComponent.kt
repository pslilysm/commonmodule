package pers.cxd.corelibrary.base

import android.os.Bundle

/**
 * @author cxd
 * Created on 2022/8/12 13:46
 */
interface MvpUiComponent<P : Presenter<*, Any>, M : Any> : UiComponent, BaseView {

    fun getPresenter(): P

    fun getModel(): M

    override fun setUp(savedInstanceState: Bundle?) {
        getPresenter().attach(this, getModel())
    }

    override fun performOnDestroy() {
        super.performOnDestroy()
        getPresenter().detach()
    }

}