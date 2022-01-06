package pers.cxd.corelibrary;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventHandler extends Handler {

    public interface EventCallback {
        void handleEvent(@NonNull Message msg);
    }

    private static final Class<?>[] sConstructorClasses = new Class[]{Looper.class};

    /**
     * @return a default EventHandler which bind MainLooper;
     */
    public static EventHandler getDefault(){
        return SingletonFactory.findOrCreate(EventHandler.class, sConstructorClasses, Looper.getMainLooper());
    }

    public EventHandler(@NonNull Looper looper) {
        this(looper, null);
    }

    public EventHandler(@NonNull Looper looper, @Nullable Handler.Callback callback) {
        super(looper, callback);
    }

    private final ConcurrentMap<Integer, List<EventCallback>> mMultiCallbacks = new ConcurrentHashMap<>();

    public void registerEvent(int eventCode, EventCallback callback){
//        synchronized (mMultiCallbacks){
//            List<EventCallback> callbacks = mMultiCallbacks.get(eventCode);
//            if (callbacks == null){
//                callbacks = new ArrayList<>();
//                mMultiCallbacks.put(eventCode, callbacks);
//            }
//            callbacks.add(callback);
//        }
        mMultiCallbacks.computeIfAbsent(eventCode, i -> new CopyOnWriteArrayList<>()).add(callback);
    }

    public void unregisterEvent(int eventCode, EventCallback callback){
        mMultiCallbacks.getOrDefault(eventCode, Collections.emptyList()).removeIf(eventCallback -> eventCallback == callback);
    }

    public void unregisterAllEvent(EventCallback callback){
        mMultiCallbacks.values().forEach(eventCallbacks -> eventCallbacks.remove(callback));
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
        List<EventCallback> callbacks = mMultiCallbacks.getOrDefault(msg.what, Collections.emptyList());
        callbacks.forEach(eventCallback -> eventCallback.handleEvent(msg));
    }
    
}
