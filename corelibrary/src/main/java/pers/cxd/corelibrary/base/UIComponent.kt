package pers.cxd.corelibrary.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

/**
 * UI components are usually implemented by Activity, Fragment, and Dialog.
 * The purpose of using this interface is to integrate the three components mentioned in the appeal and standardize their functions.
 *
 * @param VB the view binding
 * @author pslilysm
 * @since 1.0.0
 */
interface UIComponent<VB : ViewBinding> : LifecycleOwner {

    /**
     * Returns null if you have yourself business ui logic
     */
    val mViewBinding: VB?

    /**
     * Setup the UIComponent
     *
     * @param savedInstanceState null if not have any saved state
     */
    fun setup(savedInstanceState: Bundle?)

    /**
     * @return the ui context
     */
    fun getContext(): Context

    /**
     * Start to a activity
     *
     * @param intent the intent you wanna start
     */
    fun startActivity(intent: Intent)

    /**
     * Start to a activity for result
     *
     * @param intent the intent you wanna start
     * @param requestCode the request code
     */
    fun startActivityForResult(intent: Intent, requestCode: Int)

    /**
     * @return layout inflater for ui renderer
     */
    fun getLayoutInflater(): LayoutInflater

    /**
     * Calling on destroy
     */
    fun performOnDestroy() {
        if (this is BaseView) {
            (this as BaseView).releaseView()
        }
    }
}