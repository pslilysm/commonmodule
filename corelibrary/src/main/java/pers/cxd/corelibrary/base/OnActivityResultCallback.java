package pers.cxd.corelibrary.base;

import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * A callback support to {@link UiComponent#registerActivityResultCallback(OnActivityResultCallback)}
 *
 * @author pslilysm
 * @since 1.0.4
 */
public interface OnActivityResultCallback {

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

}
