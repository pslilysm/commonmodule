package pers.cxd.corelibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public interface Component {

    int getLayoutId();
    void setUp(@Nullable Bundle savedInstanceState);

}
