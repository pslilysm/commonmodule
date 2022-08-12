package pers.cxd.corelibrary.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater

/**
 * UI components are usually implemented by Activity, Fragment, and Dialog.
 * The purpose of using this interface is to integrate the three components mentioned in the appeal and standardize their functions.
 *
 * @author pslilysm
 * @since 1.0.0
 */
interface UiComponent {
    val layoutId: Int
    fun setUp(savedInstanceState: Bundle?)
    fun getContext(): Context
    fun startActivity(intent: Intent)
    fun startActivityForResult(intent: Intent, requestCode: Int)
    fun getLayoutInflater(): LayoutInflater
    fun registerActivityResultCallback(callback: OnActivityResultCallback) {
        UiComponentPlugins.registerActivityResultCallback(this, callback)
    }

    fun performOnDestroy() {
        UiComponentPlugins.unregisterActivityResultCallbacks(this)
        if (this is BaseView) {
            (this as BaseView).releaseView()
        }
    }
}