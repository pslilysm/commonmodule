package pers.cxd.corelibrary.base;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface Component {

    int getLayoutId();
    void setUp(@Nullable Bundle savedInstanceState);
    <T extends Fragment> T findOrCreateFmt(Class<T> clazz, int position);
    void startActivityForResult(Intent intent, int requestCode);

}
