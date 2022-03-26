package pers.cxd.corelibrary;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * EventHandler is designed to send event between {@link pers.cxd.corelibrary.base.UiComponent}
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class EventHandler extends Handler {

    private static final Class<?>[] sConstructorClasses = new Class[]{Looper.class};
    private final ConcurrentMap<Integer, List<EventCallback>> mMultiCallbacks = new ConcurrentHashMap<>();

    public EventHandler(@NonNull Looper looper) {
        this(looper, null);
    }

    public EventHandler(@NonNull Looper looper, @Nullable Handler.Callback callback) {
        super(looper, callback);
    }

    /**
     * @return a default EventHandler which bind MainLooper;
     */
    public static EventHandler getDefault() {
        return SingletonFactory.findOrCreate(EventHandler.class, sConstructorClasses, Looper.getMainLooper());
    }

    public void registerEvent(int eventCode, EventCallback callback) {
        mMultiCallbacks.computeIfAbsent(eventCode, i -> new CopyOnWriteArrayList<>()).add(callback);
    }

    public void unregisterEvent(int eventCode, EventCallback callback) {
        mMultiCallbacks.getOrDefault(eventCode, Collections.emptyList()).removeIf(eventCallback -> eventCallback == callback);
    }

    public void unregisterAllEvent(EventCallback callback) {
        mMultiCallbacks.values().forEach(eventCallbacks -> eventCallbacks.remove(callback));
    }

    /**
     * if current thread == the thread we bind, handle the event directly
     *
     * @param event to send
     */
    public void sendEventOpted(Message event) {
        if (Looper.myLooper() == getLooper()) {
            handleMessage(event);
            event.recycle();
        } else {
            sendMessage(event);
        }
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        List<EventCallback> callbacks = mMultiCallbacks.getOrDefault(msg.what, Collections.emptyList());
        callbacks.forEach(eventCallback -> eventCallback.handleEvent(msg));
    }

    public interface EventCallback {
        void handleEvent(@NonNull Message msg);
    }

}
