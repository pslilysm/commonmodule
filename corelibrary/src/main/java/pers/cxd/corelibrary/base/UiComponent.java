package pers.cxd.corelibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface UiComponent {

    int getLayoutId();
    void setUp(@Nullable Bundle savedInstanceState);
    <T extends Fragment> T findOrCreateFmt(Class<T> clazz, int position);
    Context getContext();
    void startActivity(Intent intent);
    void startActivityForResult(Intent intent, int requestCode);
    LayoutInflater getLayoutInflater();

}
