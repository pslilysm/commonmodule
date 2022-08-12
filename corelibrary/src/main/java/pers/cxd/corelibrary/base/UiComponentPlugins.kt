package pers.cxd.corelibrary.base

import android.util.Log
import androidx.collection.ArrayMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pers.cxd.corelibrary.util.ExceptionUtil.rethrow
import pers.cxd.corelibrary.util.ThreadUtil
import pers.cxd.corelibrary.util.reflection.ReflectionUtil


/**
 * Provide some default UiComponent interface implementation
 *
 * @author pslilysm
 * @since 1.0.0
 */
object UiComponentPlugins {
    val sSimpleFinder: FragmentFinder = object : FragmentFinder {
        override fun <T : Fragment?> findFragment(
            fmtClass: Class<out Fragment?>,
            vararg args: Any?
        ): T? {
            val manager = args[0] as FragmentManager
            return manager.findFragmentByTag(fmtClass.name) as T?
        }
    }
    val sViewPager2Finder: FragmentFinder = object : FragmentFinder {
        override fun <T : Fragment?> findFragment(
            fmtClass: Class<out Fragment?>,
            vararg args: Any?
        ): T? {
            val manager = args[0] as FragmentManager
            val position = args[1] as Int
            return manager.findFragmentByTag("f$position") as T?
        }
    }
    private val TAG = "DEBUG_" + UiComponentPlugins::class.java.simpleName
    private val sComponentCallbackMap: MutableMap<UiComponent, MutableList<OnActivityResultCallback?>> =
        ArrayMap()

    fun <T : Fragment?> findOrCreateFmt(
        fmtClass: Class<T>,
        finder: FragmentFinder,
        vararg args: Any?
    ): T {
        return findOrCreateFmt1(fmtClass, finder, object : FragmentCreator<T> {
            override fun create(fmtClazz: Class<T>): T {
                return try {
                    ReflectionUtil.newInstance(fmtClazz)
                } catch (e: ReflectiveOperationException) {
                    throw rethrow(e)
                }
            }
        }, args)
    }

    fun <T : Fragment?> findOrCreateFmt1(
        fmtClass: Class<T>,
        finder: FragmentFinder,
        creator: FragmentCreator<T>,
        vararg args: Any?
    ): T {
        var fmt = finder.findFragment<T>(fmtClass, *args)
        Log.i(TAG, "findOrCreateFmt: $fmt")
        if (fmt == null) {
            fmt = creator.create(fmtClass)
        }
        return fmt!!
    }

    fun registerActivityResultCallback(
        component: UiComponent,
        callback: OnActivityResultCallback?
    ) {
        ThreadUtil.checkIsMainThread()
        sComponentCallbackMap.computeIfAbsent(component) { o: UiComponent? -> ArrayList() }
            .add(callback)
    }

    fun getActivityResultCallbacks(component: UiComponent): List<OnActivityResultCallback?> {
        ThreadUtil.checkIsMainThread()
        return sComponentCallbackMap.computeIfAbsent(component) { o: UiComponent? -> ArrayList() }
    }

    fun unregisterActivityResultCallbacks(component: UiComponent) {
        ThreadUtil.checkIsMainThread()
        sComponentCallbackMap.remove(component)
    }
}