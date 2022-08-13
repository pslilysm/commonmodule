package pers.cxd.commonmodule.activities.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import pers.cxd.commonmodule.ReflectionTest
import pers.cxd.commonmodule.activities.main.MainActivity
import pers.cxd.commonmodule.databinding.ActivitySplashBinding
import pers.cxd.corelibrary.Singleton
import pers.cxd.corelibrary.base.MvpUIComponent
import pers.cxd.corelibrary.base.UIComponentPlugins
import pers.cxd.corelibrary.util.GlobalExecutors
import pers.cxd.corelibrary.util.GsonUtil
import pers.cxd.corelibrary.util.ScreenUtil
import pers.cxd.corelibrary.util.ToastUtil
import pers.cxd.corelibrary.util.reflection.ReflectionUtil
import java.util.concurrent.TimeUnit


@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity(),
    MvpUIComponent<SplashContract.Model, SplashContract.View, SplashContract.Presenter, ActivitySplashBinding>,
    SplashContract.View {

    companion object {
        private const val TAG = "CXD-SplashActivity"
    }

    override fun getModel(): SplashContract.Model {
        return SplashModel
    }

    override fun getView(): SplashContract.View {
        return this
    }

    override fun getPresenter(): SplashContract.Presenter {
        return SplashPresenter
    }

    override fun getContext(): Context {
        return this
    }

    private val mBindingSingleton = object : Singleton<ActivitySplashBinding>(){
        override fun create(): ActivitySplashBinding {
            return ActivitySplashBinding.inflate(layoutInflater)
        }
    }

    override val mViewBinding: ActivitySplashBinding
        get() = mBindingSingleton.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        UIComponentPlugins.initUIComponent(this)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("HardwareIds")
    override fun setUp(savedInstanceState: Bundle?) {
        super.setUp(savedInstanceState)
        getPresenter().getSomeData("1", "2")
        getPresenter().register("admin", "123456")
        testReflection()
        val rv = mViewBinding.rv
        val data: MutableList<String> = ArrayList()
        for (i in 0..99) {
            data.add(i.toString())
        }
        rv.adapter = Adapter(data)
        //        rv.scrollToPosition(15);
        Log.i(
            TAG, "setUp: android_id -> " + Settings.Secure.getString(
                contentResolver, "android_id"
            )
        )
        ToastUtil.showShort(Settings.Secure.getString(contentResolver, "android_id"))
        val jsonString = ("{'Bob' : 112,"
                + "'Jenny' : \"1123\", "
                + "'Steve' : 123.0123" +
                "}")
        val map = GsonUtil.jsonToMap(jsonString, Any::class.java)
        Log.i(TAG, "setUp: " + map.javaClass)
        map.forEach { (s: String, obj: Any) ->
            Log.i(TAG, "accept: $s")
            Log.i(TAG, "accept: " + obj.javaClass)
        }
        GlobalExecutors.io().schedule({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }, 1, TimeUnit.SECONDS)
    }

    private fun testReflection() {
        var activityThread: Any
        var mH: Handler
        try {
            var o = SystemClock.elapsedRealtimeNanos()
            activityThread = ReflectionUtil.invokeStaticMethod(
                "android.app.ActivityThread",
                "currentActivityThread"
            )
            mH = ReflectionUtil.getFieldValue(activityThread, "mH")
            Log.i(TAG, "setUp: 1 " + (SystemClock.elapsedRealtimeNanos() - o))

            o = SystemClock.elapsedRealtimeNanos()
            activityThread =
                ReflectionUtil.invokeStaticMethod(
                    "android.app.ActivityThread",
                    "currentActivityThread"
                )
            mH = ReflectionUtil.getFieldValue(activityThread, "mH")
            Log.i(TAG, "setUp: 2 " + (SystemClock.elapsedRealtimeNanos() - o))

            o = SystemClock.elapsedRealtimeNanos()
            activityThread =
                ReflectionUtil.invokeStaticMethod(
                    "android.app.ActivityThread",
                    "currentActivityThread"
                )
            mH = ReflectionUtil.getFieldValue(activityThread, "mH")
            Log.i(TAG, "setUp: 3 " + (SystemClock.elapsedRealtimeNanos() - o))

            o = SystemClock.elapsedRealtimeNanos()
            var view = ReflectionUtil.newInstance(View::class.java, Context::class.java, this@SplashActivity)
            Log.i(TAG, "setUp: new instance 1 cost -> " + (SystemClock.elapsedRealtimeNanos() - o) + " ns")

            o = SystemClock.elapsedRealtimeNanos()
            view = ReflectionUtil.newInstance(View::class.java, Context::class.java, this@SplashActivity)
            Log.i(TAG, "setUp: new instance 2 cost -> " + (SystemClock.elapsedRealtimeNanos() - o) + " ns")

            o = SystemClock.elapsedRealtimeNanos()
            ReflectionUtil.invokeMethod<Any>(mViewBinding.rv, "setVisibility", Int::class.java, View.GONE)
            Log.i(TAG, "setUp: setVisibility 1 cost -> " + (SystemClock.elapsedRealtimeNanos() - o) + " ns")

            o = SystemClock.elapsedRealtimeNanos()
            ReflectionUtil.invokeMethod<Any>(mViewBinding.rv, "setVisibility", Int::class.java, View.VISIBLE)
            Log.i(TAG, "setUp: setVisibility 2 cost -> " + (SystemClock.elapsedRealtimeNanos() - o) + " ns")

            ReflectionUtil.invokeStaticMethod<Any>(ReflectionTest::class.java, "setsTest", String::class.java, "test111")
            ReflectionUtil.invokeStaticMethod<Any>(ReflectionTest::class.java, "printsTest")
            ReflectionUtil.setStaticFiledValue(ReflectionTest::class.java, "sTest", "test222")
            Log.i(TAG, "testReflection: sTest -> " + ReflectionUtil.getStaticFieldValue(ReflectionTest::class.java, "sTest"))

            val reflectionTest = ReflectionUtil.newInstance(ReflectionTest::class.java)
            ReflectionUtil.setFieldValue(reflectionTest, "mTest", "test333")
            Log.i(TAG, "testReflection: mTest -> " + ReflectionUtil.getFieldValue(reflectionTest, "mTest"))

        } catch (e: ReflectiveOperationException) {
            Log.w(TAG, e)
        }
    }

    override fun onSomeDataGotten(data: Any) {}

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private class Adapter(val data: List<String>) : RecyclerView.Adapter<Adapter.VH>() {
        var i = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//            Log.d(TAG, "onCreateViewHolder() called i  = " + (++i));
            val tv = TextView(parent.context)
            tv.gravity = Gravity.CENTER
            tv.setPaddingRelative(0, ScreenUtil.dip2px(8f), 0, ScreenUtil.dip2px(8f))
            tv.setTextColor(Color.BLACK)
            tv.width = ScreenUtil.width
            return VH(tv)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
//            Log.d(TAG, "onBindViewHolder() called with: position = [" + position + "]");
            (holder.itemView as TextView).text = data.get(position)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
        companion object {
            private val TAG = "DEBUG_Adapter"
        }
    }

}