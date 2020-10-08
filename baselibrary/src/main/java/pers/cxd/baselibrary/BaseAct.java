package pers.cxd.baselibrary;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pers.cxd.utillibrary.Log;

public abstract class BaseAct extends AppCompatActivity {

    protected final String TAG = Log.TAG + this.getClass().getSimpleName();

    protected abstract int getLayoutId();
    protected abstract void setUp();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUp();
    }
}
