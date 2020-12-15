package pers.cxd.corelibrary;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;

import java.util.ArrayList;
import java.util.List;

public class EventHandler extends Handler {

    private static EventHandler sDefault;

    public interface EventCallback {
        void handleEvent(@NonNull Message msg);
    }

    /**
     * @return a default EventHandler which bind MainLooper;
     */
    public static EventHandler getDefault(){
        if (sDefault == null){
            synchronized (EventHandler.class){
                if (sDefault == null){
                    sDefault = new EventHandler(Looper.getMainLooper());
                }
            }
        }
        return sDefault;
    }

    public EventHandler() {
        super();
    }

    public EventHandler(@Nullable Handler.Callback callback) {
        super(callback);
    }

    public EventHandler(@NonNull Looper looper) {
        super(looper);
    }

    public EventHandler(@NonNull Looper looper, @Nullable Handler.Callback callback) {
        super(looper, callback);
    }

    private final SparseArrayCompat<List<EventCallback>> mMultiCallbacks = new SparseArrayCompat<>();

    public void registerEvent(int eventCode, EventCallback callback){
        synchronized (mMultiCallbacks){
            List<EventCallback> callbacks = mMultiCallbacks.get(eventCode);
            if (callbacks == null){
                callbacks = new ArrayList<>();
                mMultiCallbacks.put(eventCode, callbacks);
            }
            callbacks.add(callback);
        }
    }

    public void unregisterEvent(int eventCode, EventCallback callback){
        synchronized (mMultiCallbacks){
            List<EventCallback> callbacks = mMultiCallbacks.get(eventCode);
            if (callbacks != null){
                callbacks.remove(callback);
                if (callbacks.size() == 0){
                    mMultiCallbacks.remove(eventCode);
                }
            }
        }
    }

    public void unregisterEvent(EventCallback callback){
        synchronized (mMultiCallbacks){
            for (int i = 0; i < mMultiCallbacks.size(); i++){
                List<EventCallback> callbacks = mMultiCallbacks.valueAt(i);
                if (callbacks != null){
                    callbacks.remove(callback);
                    if (callbacks.size() == 0){
                        // why i--? because on the next loop we'll call size(), this method will do gc and move elements forward 1 index
                        mMultiCallbacks.removeAt(i--);
                    }
                }
            }
        }
    }

    /**
     * if current thread == the thread we bind, handle the event directly
     * @param event to send
     */
    public void sendEventOpted(Message event){
        if (Looper.myLooper() == getLooper()){
            handleMessage(event);
            event.recycle();
        }else {
            sendMessage(event);
        }
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        synchronized (mMultiCallbacks){
            for (int i = 0; i < mMultiCallbacks.size(); i++){
                if (msg.what == mMultiCallbacks.keyAt(i)){
                    for (EventCallback callback : mMultiCallbacks.valueAt(i)){
                        callback.handleEvent(msg);
                    }
                }
            }
        }
    }
    
}
