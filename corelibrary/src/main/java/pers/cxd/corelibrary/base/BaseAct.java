package pers.cxd.corelibrary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pers.cxd.corelibrary.Log;

public abstract class BaseAct extends AppCompatActivity implements Component {

    protected final String TAG = Log.TAG + this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setUp(savedInstanceState);
    }

}
