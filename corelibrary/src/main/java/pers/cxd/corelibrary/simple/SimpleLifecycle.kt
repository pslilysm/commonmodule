package pers.cxd.corelibrary.simple

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * make all implementation of [Application.ActivityLifecycleCallbacks] be default, the purpose is to simplify code
 * when some callback don't need to be used
 *
 * @author pslilysm
 * @since 2.0.0
 * Created on 2022/8/12 18:41
 */
interface SimpleLifecycle: Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}