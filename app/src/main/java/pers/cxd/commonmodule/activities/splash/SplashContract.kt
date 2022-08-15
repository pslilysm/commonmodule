package pers.cxd.commonmodule.activities.splash

import com.google.gson.JsonElement
import pers.cxd.corelibrary.base.BaseView

interface SplashContract {
    interface View : BaseView {
        fun updateTestUI(data: JsonElement)
    }

    interface Model {
        suspend fun testModel(): Result<JsonElement>
    }

    abstract class Presenter : pers.cxd.corelibrary.base.Presenter<View, Model>() {
        abstract fun test()
    }
}