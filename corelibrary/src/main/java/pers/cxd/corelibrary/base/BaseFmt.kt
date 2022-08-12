package pers.cxd.corelibrary.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.function.Consumer

/**
 * A Base Fragment extends Fragment which implements UiComponent, FragmentFinder.
 * Support to find fragment when our fragment has been low-memory-killed.
 *
 * @author pslilysm
 * @since 1.0.0
 */
abstract class BaseFmt : Fragment(), UiComponent, FragmentFinder {
    protected val TAG = "DEBUG_" + this.javaClass.simpleName
    protected var mContentView: ViewGroup? = null
    protected fun <V : View?> findViewById(id: Int): V {
        return mContentView!!.findViewById(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mContentView == null) {
            val layoutId = layoutId
            if (layoutId != 0) {
                mContentView = inflater.inflate(layoutId, container, false) as ViewGroup
            }
            setUp(savedInstanceState)
        }
        return mContentView
    }

    override fun <T : Fragment?> findFragment(
        fmtClass: Class<out Fragment?>,
        vararg args: Any?
    ): T? {
        return UiComponentPlugins.sSimpleFinder.findFragment(fmtClass, *args)
    }

    override fun getContext(): Context {
        return requireContext()
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