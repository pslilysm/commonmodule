package pers.cxd.corelibrary.base

import android.content.Intent

/**
 * A callback support to [UiComponent.registerActivityResultCallback]
 *
 * @author pslilysm
 * @since 1.0.4
 */
interface OnActivityResultCallback {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}