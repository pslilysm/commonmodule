package pers.cxd.commonmodule.activities.splash

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import pers.cxd.commonmodule.R
import pers.cxd.corelibrary.base.MvpUiComponent
import pers.cxd.corelibrary.util.GsonUtil
import pers.cxd.corelibrary.util.ScreenUtil
import pers.cxd.corelibrary.util.ToastUtil
import pers.cxd.corelibrary.util.reflection.ReflectionUtil


@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity(),
    MvpUiComponent<SplashContract.Presenter, SplashContract.Model>,
    SplashContract.View {

    companion object {
        private const val TAG = "CXD-SplashActivity"
    }

    override fun getPresenter(): SplashContract.Presenter {
        return SplashPresenter
    }

    override fun getModel(): SplashContract.Model {
        return SplashModel
    }

    override fun getContext(): Context {
        return this
    }

    override val layoutId: Int
        get() = R.layout.act_splash

    @SuppressLint("HardwareIds")
    override fun setUp(savedInstanceState: Bundle?) {
        super.setUp(savedInstanceState)
        getPresenter().getSomeData("1", "2")
        getPresenter().register("admin", "123456")
        var activityThread: Any
        var mH: Handler
        try {
            var o = SystemClock.elapsedRealtimeNanos()
            activityThread = ReflectionUtil.invokeStaticMethod(
                "android.app.ActivityThread",
                "currentActivityThread"
            )
            Log.i(TAG, "setUp: $activityThread")
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
            mH.post(Runnable {
                //                    Toast.makeText(SplashActivity.this, "cao ni ma", Toast.LENGTH_SHORT).show();
            })
            activityThread =
                ReflectionUtil.invokeStaticMethod(
                    "android.app.ActivityThread",
                    "currentActivityThread"
                )
            Log.i(TAG, "setUp: $activityThread")
        } catch (e: ReflectiveOperationException) {
            Log.w(TAG, e)
        }
        val rv = findViewById<RecyclerView>(R.id.rv)
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
        Log.i(TAG, "setUp: " + map.size)
        Log.i(TAG, "setUp: " + map.javaClass)
        map.forEach { (s: String, obj: Any) ->
            Log.i(TAG, "accept: $s")
            Log.i(TAG, "accept: " + obj.javaClass)
        }
    }

    override fun onSomeDataGotten(data: Any) {}

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