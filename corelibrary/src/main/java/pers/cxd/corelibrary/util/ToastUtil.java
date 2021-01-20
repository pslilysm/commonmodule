package pers.cxd.corelibrary.util;

import android.os.Looper;
import android.widget.Toast;

import pers.cxd.corelibrary.ApplicationHolder;
import pers.cxd.corelibrary.EventHandler;

public class ToastUtil {

    public static void showShortToast(CharSequence text){
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(CharSequence text){
        showToast(text, Toast.LENGTH_LONG);
    }

    private static void showToast(CharSequence text, int duration){
        ShowToastRunnable runnable = new ShowToastRunnable(text, duration);
        if (Looper.myLooper() == null){
            EventHandler.getDefault().post(runnable);
        }else {
            runnable.run();
        }
    }

    private static class ShowToastRunnable implements Runnable{

        private final CharSequence text;
        private final int length;

        public ShowToastRunnable(CharSequence text, int length) {
            this.text = text;
            this.length = length;
        }

        @Override
        public void run() {
            Toast.makeText(ApplicationHolder.get(), text, length).show();
        }

    }

}
