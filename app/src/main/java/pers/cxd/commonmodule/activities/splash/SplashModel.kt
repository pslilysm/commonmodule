package pers.cxd.commonmodule.activities.splash

import com.google.gson.JsonElement
import pers.cxd.commonmodule.network.DemoApiInterface
import pers.cxd.commonmodule.network.DemoRetrofitClient
import pers.cxd.corelibrary.util.function.SuspendFunction

internal object SplashModel : SplashContract.Model {
    override suspend fun testModel(): Result<JsonElement> {
        return DemoRetrofitClient.fetchData(object : SuspendFunction<JsonElement> {
            override suspend fun invoke(): JsonElement {
                return DemoRetrofitClient.getApi(DemoApiInterface::class.java).testGet()
            }
        })
    }
}