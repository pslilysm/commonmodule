package pers.cxd.corelibrary.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.function.Consumer

/**
 * A Base Activity extends AppCompatActivity which implements UiComponent, FragmentFinder.
 * Support to find fragment when our activity has been low-memory-killed.
 *
 * @author pslilysm
 * @since 1.0.0
 */
abstract class BaseAct : AppCompatActivity(), UiComponent, FragmentFinder {
    @kotlin.jvm.JvmField
    protected val TAG = "DEBUG_" + this.javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutId = layoutId
        if (layoutId != 0) {
            setContentView(layoutId)
        }
        setUp(savedInstanceState)
    }

    override fun getContext(): Context {
        return this
    }

    /**
     * default implementation by [UiComponentPlugins.sSimpleFinder]
     */
    override fun <T : Fragment?> findFragment(
        fmtClass: Class<out Fragment?>,
        vararg args: Any?
    ): T? {
        return UiComponentPlugins.sSimpleFinder.findFragment(fmtClass, *args)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UiComponentPlugins.getActivityResultCallbacks(this)
            .forEach(Consumer { callback: OnActivityResultCallback? ->
                callback!!.onActivityResult(
                    requestCode,
                    resultCode,
                    data
                )
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        performOnDestroy()
    }
}