package pers.cxd.corelibrary.util.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * Default implementation of {@link ResourcePool}
 *
 * @author pslilysm
 * @since 1.0.0
 * @param <R> the type of resource
 */
public class ResourcePoolImpl<R> implements ResourcePool<R> {

    private final Queue<R> mResourceQueue = new ConcurrentLinkedQueue<>();
    private final Queue<Thread> mWaitQueue = new ConcurrentLinkedQueue<>();

    @Override
    public R getResource() {
        R r = mResourceQueue.poll();
        if (r == null) {
            Thread curTh = Thread.currentThread();
            mWaitQueue.offer(curTh);
            LockSupport.park(mResourceQueue);
            if (curTh.isInterrupted()) {
                mWaitQueue.remove(curTh);
                return null;
            }
            r = mResourceQueue.poll();
        }
        return r == null ? getResource() : r;
    }

    @Override
    public void storeResource(R r) {
        mResourceQueue.offer(r);
        Thread waitTh = mWaitQueue.poll();
        if (waitTh != null) {
            LockSupport.unpark(waitTh);
        }
    }

    public Queue<R> getResourceQueue() {
        return mResourceQueue;
    }

    public Queue<Thread> getWaitQueue() {
        return mWaitQueue;
    }

}
