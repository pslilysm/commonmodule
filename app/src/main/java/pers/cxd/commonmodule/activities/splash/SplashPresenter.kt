package pers.cxd.commonmodule.activities.splash

import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal object SplashPresenter : SplashContract.Presenter() {

    private const val TAG = "CXD-SplashPresenter"

    override fun test() {
        lifecycleScope.launch(Dispatchers.Main) {
            val result = mModel!!.testModel()
            Log.i(TAG, "test: $result")
            if (notDetach()) {
                if (result.isSuccess) {
                    mView!!.updateTestUI(result.getOrNull()!!)
                }
            }
        }
    }

}