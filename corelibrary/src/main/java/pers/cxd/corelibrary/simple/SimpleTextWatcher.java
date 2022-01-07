package pers.cxd.corelibrary.simple;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * make all implementation of {@link TextWatcher} be default, the purpose is to simplify code
 * when some callback don't need to be used
 *
 * @author pslilysm
 * @since 1.0.0
 */
public interface SimpleTextWatcher extends TextWatcher {

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    default void afterTextChanged(Editable s) {

    }

}
