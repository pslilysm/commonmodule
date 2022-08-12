package pers.cxd.commonmodule.activities.splash

import androidx.collection.ArrayMap
import io.reactivex.rxjava3.core.Observable
import pers.cxd.commonmodule.network.DemoApiInterface
import pers.cxd.commonmodule.network.DemoRetrofitClient

internal object SplashModel : SplashContract.Model {
    var someDataMap: ArrayMap<String, String>? = null
    override fun someDataModelAvailable(): Boolean {
        return someDataMap == null
    }

    override fun someDataModel(arg1: String, arg2: String): Observable<Any> {
        someDataMap = ArrayMap(4)
        someDataMap!!["arg1"] = arg1
        someDataMap!!["arg2"] = arg2
        return DemoRetrofitClient.getApi(DemoApiInterface::class.java)
            .postSample(someDataMap)
    }

    override fun clearSomeDataModel() {
        if (someDataMap != null) {
            someDataMap!!.clear()
            someDataMap = null
        }
    }

    override fun registerModel(accountName: String, password: String): Observable<Void> {
        return DemoRetrofitClient.getApi(DemoApiInterface::class.java)
            .register(accountName, password)
    }
}