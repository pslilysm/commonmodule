package pers.cxd.corelibrary.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Presenter is responsible for the interaction between the view and the model
 *
 * @param <V> the view provide interface to touch ui
 * @param <M> the model provide data source to view
 * @author pslilysm
 * @since 1.0.0
</M></V> */
open class Presenter<V : BaseView, M> : LifecycleOwner {

    @kotlin.jvm.JvmField
    protected var mView: V? = null
    @kotlin.jvm.JvmField
    protected var mModel: M? = null
    override fun getLifecycle(): Lifecycle {
        return mView!!.lifecycle
    }

    open fun attach(v: V, m: M) {
        mView = v
        mModel = m
    }

    open fun detach() {
        mView = null
        mModel = null
    }

    protected open fun notDetach(): Boolean {
        return mView != null && mModel != null
    }
}