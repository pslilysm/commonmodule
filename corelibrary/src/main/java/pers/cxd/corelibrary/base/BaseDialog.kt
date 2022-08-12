package pers.cxd.corelibrary.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import pers.cxd.corelibrary.util.ScreenUtil

/**
 * A Base Dialog implements UiComponent, DialogInterface.OnDismissListener.
 * Easy to design various dialogs
 *
 * @author pslilysm
 * @since 1.1.0
 */
abstract class BaseDialog protected constructor(protected val mBase: UiComponent) : UiComponent,
    DialogInterface.OnDismissListener {
    protected val mBuilder: AlertDialog.Builder
    private val mContentView: View
    protected var mDialog: AlertDialog? = null
    fun <V : View?> findViewById(id: Int): V {
        return mContentView.findViewById(id)
    }

    override fun onDismiss(dialog: DialogInterface) {
        performOnDestroy()
    }

    fun show() {
        if (mDialog == null) {
            mDialog = mBuilder.create()
        }
        mDialog!!.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(mDialog!!.window!!.attributes)
        lp.width = ScreenUtil.width - ScreenUtil.dip2px((36 * 2).toFloat())
        mDialog!!.window!!.attributes = lp
        mDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun dismiss() {
        if (mDialog != null) {
            mDialog!!.dismiss()
        }
    }

    fun showSoftKeyboard(editText: EditText) {
        editText.postDelayed({
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
            val imm =
                mBase.getContext()!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            editText.setSelection(editText.text.length)
        }, 200)
    }

    fun hideSoftKeyboard(editText: EditText) {
        val imm = mBase.getContext()!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    override fun getContext(): Context {
        return mBase.getContext()
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        mBase.startActivityForResult(intent, requestCode)
    }

    override fun getLayoutInflater(): LayoutInflater {
        return mBase.getLayoutInflater()
    }

    override fun startActivity(intent: Intent) {
        mBase.startActivity(intent)
    }

    override fun registerActivityResultCallback(callback: OnActivityResultCallback) {
        mBase.registerActivityResultCallback(callback)
    }

    init {
        mContentView = getLayoutInflater().inflate(layoutId, null)
        mBuilder = AlertDialog.Builder(mBase.getContext()!!)
            .setView(mContentView)
            .setCancelable(true)
            .setOnDismissListener(this)
        setUp(null)
    }
}