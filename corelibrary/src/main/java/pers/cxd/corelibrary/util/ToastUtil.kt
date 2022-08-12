package pers.cxd.corelibrary.util

import android.os.Looper
import android.widget.Toast
import pers.cxd.corelibrary.AppHolder
import pers.cxd.corelibrary.EventHandler

/**
 * Miscellaneous [Toast] utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object ToastUtil {
    @kotlin.jvm.JvmStatic
    fun showShort(text: CharSequence) {
        showToast(text, Toast.LENGTH_SHORT)
    }

    @kotlin.jvm.JvmStatic
    fun showLong(text: CharSequence) {
        showToast(text, Toast.LENGTH_LONG)
    }

    private fun showToast(text: CharSequence, duration: Int) {
        val runnable = ShowToastRunnable(text, duration)
        if (Looper.myLooper() == null) {
            EventHandler.default.post(runnable)
        } else {
            runnable.run()
        }
    }

    private class ShowToastRunnable(private val text: CharSequence, private val length: Int) :
        Runnable {
        override fun run() {
            Toast.makeText(AppHolder.get(), text, length).show()
        }
    }
}