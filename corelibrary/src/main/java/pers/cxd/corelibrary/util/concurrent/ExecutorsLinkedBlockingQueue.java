package pers.cxd.corelibrary.util.concurrent;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Executors dedicated LinkedBlockingQueue
 *
 * @author pslilysm
 * Created on 2022/9/27 15:56
 */
public class ExecutorsLinkedBlockingQueue extends LinkedBlockingQueue<Runnable> {

    private ThreadPoolExecutor mExecutor;

    public ExecutorsLinkedBlockingQueue() {
        super();
    }

    public ExecutorsLinkedBlockingQueue(int capacity) {
        super(capacity);
    }

    public ExecutorsLinkedBlockingQueue(Collection<? extends Runnable> c) {
        super(c);
    }

    public void setExecutor(ThreadPoolExecutor mExecutor) {
        this.mExecutor = mExecutor;
    }

    @Override
    public boolean offer(Runnable e) {
        Objects.requireNonNull(mExecutor, "please call setExecutor");
        if (mExecutor.getPoolSize() < mExecutor.getMaximumPoolSize()
                && mExecutor.getActiveCount() == mExecutor.getPoolSize()) {
            // if executor's pool size < max size
            // and all thread are executing
            // we reject queue task for make executors add more workers to handle the task
            return false;
        }
        return super.offer(e);
    }
}
