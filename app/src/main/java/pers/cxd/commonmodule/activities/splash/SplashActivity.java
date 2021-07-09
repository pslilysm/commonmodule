package pers.cxd.commonmodule.activities.splash;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pers.cxd.commonmodule.R;
import pers.cxd.corelibrary.util.ScreenUtil;
import pers.cxd.corelibrary.util.reflection.ReflectionUtil;
import pers.cxd.corelibrary.mvp.MvpAct;

public class SplashActivity extends MvpAct<SplashContract.Presenter, SplashContract.Model>
        implements SplashContract.View {

    @Override
    protected SplashContract.Presenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    protected SplashContract.Model createModel() {
        return new SplashModel();
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    public void setUp(@Nullable Bundle savedInstanceState) {
        mPresenter.getSomeData("1", "2");
        mPresenter.register("admin","123456");
        Object activityThread;
        Handler mH;
        try {
            long o = SystemClock.elapsedRealtimeNanos();
            activityThread = ReflectionUtil.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
            Log.i(TAG, "setUp: " + activityThread);
            mH = ReflectionUtil.getField(activityThread, "mH");
            Log.i(TAG, "setUp: 1 " + (SystemClock.elapsedRealtimeNanos() - o));
            o = SystemClock.elapsedRealtimeNanos();
            activityThread = ReflectionUtil.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
            mH = ReflectionUtil.getField(activityThread, "mH");
            Log.i(TAG, "setUp: 2 " + (SystemClock.elapsedRealtimeNanos() - o));
            o = SystemClock.elapsedRealtimeNanos();
            activityThread = ReflectionUtil.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
            mH = ReflectionUtil.getField(activityThread, "mH");
            Log.i(TAG, "setUp: 3 " + (SystemClock.elapsedRealtimeNanos() - o));
            mH.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SplashActivity.this, "cao ni ma", Toast.LENGTH_SHORT).show();
                }
            });
            activityThread = ReflectionUtil.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread");
            Log.i(TAG, "setUp: " + activityThread);
        } catch (ReflectiveOperationException e) {
            Log.w(TAG, e);
        }

        RecyclerView rv = findViewById(R.id.rv);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(String.valueOf(i));
        }
        rv.setAdapter(new Adapter(data));
        rv.scrollToPosition(15);
    }

    private static class Adapter extends RecyclerView.Adapter<Adapter.VH> {

        private static final String TAG = "DEBUG_CXD_Adapter";

        final List<String> data;

        public Adapter(List<String> data) {
            this.data = data;
        }

        static class VH extends RecyclerView.ViewHolder{
            public VH(@NonNull View itemView) {
                super(itemView);
            }
        }

        int i;

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder() called i  = " + (++i));
            TextView tv = new TextView(parent.getContext());
            tv.setGravity(Gravity.CENTER);
            tv.setPaddingRelative(0, ScreenUtil.dip2px(8), 0, ScreenUtil.dip2px(8));
            tv.setTextColor(Color.BLACK);
            tv.setWidth(ScreenUtil.getWidth());
            return new VH(tv);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            Log.d(TAG, "onBindViewHolder() called with: position = [" + position + "]");
            ((TextView) holder.itemView).setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    @Override
    public void onSomeDataGotten(Object data) {

    }

}
