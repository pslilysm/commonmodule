package pers.cxd.corelibrary.simple

import android.text.Editable
import android.text.TextWatcher

/**
 * make all implementation of [TextWatcher] be default, the purpose is to simplify code
 * when some callback don't need to be used
 *
 * @author pslilysm
 * @since 1.0.0
 */
interface SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {}
}