package pers.cxd.commonmodule.activities.splash;

import androidx.collection.ArrayMap;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import pers.cxd.commonmodule.network.DemoHttpClient;

class SplashModel implements SplashContract.Model {

    Map<String, String> someDataMap;

    @Override
    public boolean someDataModelAvailable() {
        return someDataMap == null;
    }

    @Override
    public Observable<Object> someDataModel(String arg1, String arg2) {
        someDataMap = new ArrayMap<>(4);
        someDataMap.put("arg1", arg1);
        someDataMap.put("arg2", arg2);
        return DemoHttpClient.getInstance().getApiInterface().postSample(someDataMap);
    }

    @Override
    public void clearSomeDataModel() {
        if (someDataMap != null){
            someDataMap.clear();
            someDataMap = null;
        }
    }

}
