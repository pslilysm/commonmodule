package pers.cxd.commonmodule.activities.splash;

import androidx.collection.ArrayMap;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import pers.cxd.commonmodule.network.DemoHttpClient;

class SplashModel implements SplashContract.Model {

    @Override
    public Observable<Object> someDataModel(String arg1, String arg2) {
        Map<String, String> map = new ArrayMap<>(2);
        map.put("arg1", arg1);
        map.put("arg2", arg2);
        return DemoHttpClient.getInstance().getApiInterface().postSample(map);
    }

}
