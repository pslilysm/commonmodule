package pers.cxd.corelibrary.base;

import android.content.Intent;

import androidx.annotation.Nullable;

public interface OnActivityResultCallback {

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);

}
