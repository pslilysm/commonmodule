package pers.cxd.commonmodule;

import android.app.Application;

import pers.cxd.commonmodule.network.DemoHttpClient;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DemoHttpClient.get().createRetrofitClient();
    }
}
