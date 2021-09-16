package pers.cxd.corelibrary.util.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

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

    @Override
    public Queue<R> getResourceQueue() {
        return mResourceQueue;
    }
}
