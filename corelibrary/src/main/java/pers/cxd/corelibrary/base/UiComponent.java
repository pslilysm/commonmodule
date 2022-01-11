package pers.cxd.corelibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

/**
 * UI components are usually implemented by Activity, Fragment, and Dialog.
 * The purpose of using this interface is to integrate the three components mentioned in the appeal and standardize their functions.
 *
 * @author pslilysm
 * @since 1.0.0
 */
public interface UiComponent {

    int getLayoutId();

    void setUp(@Nullable Bundle savedInstanceState);

    Context getContext();

    void startActivity(Intent intent);

    void startActivityForResult(Intent intent, int requestCode);

    LayoutInflater getLayoutInflater();

    void registerActivityResultCallback(OnActivityResultCallback callback);

}
