package pers.cxd.corelibrary.base

import androidx.lifecycle.LifecycleOwner

/**
 * BaseView provide normally ui update function
 *
 * @author pslilysm
 * @since 1.0.9
 */
interface BaseView : LifecycleOwner {
    fun releaseView() {}
}