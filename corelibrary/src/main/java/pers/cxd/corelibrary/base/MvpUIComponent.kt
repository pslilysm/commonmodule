package pers.cxd.corelibrary.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * A interface for assemble MVP Design-pattern
 *
 * @author pslilysm
 * Created on 2022/8/12 13:46
 */
interface MvpUIComponent<M, V : BaseView, P : Presenter<V, M>, VB: ViewBinding> : UIComponent<VB> {

    fun getView(): V

    fun getModel(): M

    fun getPresenter(): P

    override fun setUp(savedInstanceState: Bundle?) {
        getPresenter().attach(getView(), getModel())
    }

    override fun performOnDestroy() {
        super.performOnDestroy()
        getPresenter().detach()
    }

}