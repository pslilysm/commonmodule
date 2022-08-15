package pers.cxd.corelibrary.base

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding

/**
 * A Base Dialog implements UiComponent, DialogInterface.OnDismissListener.
 * Easy to design various dialogs
 *
 * @author pslilysm
 * @since 1.1.0
 */
abstract class BaseDialog<VB : ViewBinding> protected constructor(protected val mBase: UIComponent<*>) : UIComponent<VB>,
    DialogInterface.OnDismissListener {
    protected val mBuilder: AlertDialog.Builder
    protected var mDialog: AlertDialog? = null

    abstract fun initWindowLp(lp: WindowManager.LayoutParams)

    init {
        mBuilder = AlertDialog.Builder(mBase.getContext())
            .setView(mViewBinding!!.root)
            .setCancelable(true)
            .setOnDismissListener(this)
        setup(null)
    }

    override fun onDismiss(dialog: DialogInterface) {
        performOnDestroy()
    }

    override fun getLifecycle(): Lifecycle {
        throw UnsupportedOperationException("dialog not have a lifecycle")
    }

    fun show() {
        if (mDialog == null) {
            mDialog = mBuilder.create()
        }
        mDialog!!.show()
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(mDialog!!.window!!.attributes)
        initWindowLp(lp)
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

}