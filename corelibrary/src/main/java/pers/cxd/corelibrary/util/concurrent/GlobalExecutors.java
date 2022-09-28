package pers.cxd.corelibrary.util.concurrent;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import pers.cxd.corelibrary.Singleton;

/**
 * A global executors holder
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class GlobalExecutors {

    private static final AtomicInteger sIONum = new AtomicInteger();

    private static final AtomicInteger sComputeNum = new AtomicInteger();

    private static final Singleton<ScheduledExecutorService> sGlobalIOExecutor = new Singleton<ScheduledExecutorService>() {
        @Override
        protected ScheduledExecutorService create() {
            int corePoolSize = 1;
            int maxPoolSize = Runtime.getRuntime().availableProcessors() * 10;
            int keepAliveTimeSeconds = 2;
            int maxQueueSize = maxPoolSize * 0xFF;
            ExecutorsLinkedBlockingQueue workQueue = new ExecutorsLinkedBlockingQueue(maxQueueSize);
            ThreadFactory threadFactory = r -> new Thread(r, "g-io-" + sIONum.incrementAndGet() + "-thread");
            RejectedExecutionHandler rejectedExecutionHandler = (r, executor) -> {
                if (workQueue.size() < maxQueueSize) {
                    executor.execute(r);
                } else {
                    throw new RejectedExecutionException("Task " + r.toString() +
                            " rejected from " +
                            executor.toString());
                }
            };
            ThreadPoolExecutor ioES = new ThreadPoolExecutor(
                    corePoolSize,
                    maxPoolSize,
                    keepAliveTimeSeconds, TimeUnit.SECONDS,
                    workQueue,
                    threadFactory,
                    rejectedExecutionHandler
            );
            workQueue.setExecutor(ioES);
            return new ScheduledThreadPoolExecutorWrapper(ioES);
        }
    };


    private static final Singleton<ScheduledExecutorService> sGlobalComputeExecutor = new Singleton<ScheduledExecutorService>() {
        @Override
        protected ScheduledExecutorService create() {
            int corePoolSize = 1;
            int maxPoolSize = Runtime.getRuntime().availableProcessors();
            int keepAliveTimeSeconds = 2;
            int maxQueueSize = maxPoolSize * 0xF;
            ExecutorsLinkedBlockingQueue workQueue = new ExecutorsLinkedBlockingQueue(maxQueueSize);
            ThreadFactory threadFactory = r -> new Thread(r, "g-compute-" + sComputeNum.incrementAndGet() + "-thread");
            RejectedExecutionHandler rejectedExecutionHandler = (r, executor) -> {
                if (workQueue.size() < maxQueueSize) {
                    executor.execute(r);
                } else {
                    throw new RejectedExecutionException("Task " + r.toString() +
                            " rejected from " +
                            executor.toString());
                }
            };
            ThreadPoolExecutor computeES = new ThreadPoolExecutor(
                    corePoolSize,
                    maxPoolSize,
                    keepAliveTimeSeconds, TimeUnit.SECONDS,
                    workQueue,
                    threadFactory,
                    rejectedExecutionHandler
            );
            workQueue.setExecutor(computeES);
            return new ScheduledThreadPoolExecutorWrapper(computeES);
        }
    };

    /**
     * @return a global io executor, the max pool size is {@code cpu cores * 10}
     */
    public static ScheduledExecutorService io() {
        return sGlobalIOExecutor.getInstance();
    }

    /**
     * @return a global compute executor, the max pool size is cpu cores
     */
    public static ScheduledExecutorService compute() {
        return sGlobalComputeExecutor.getInstance();
    }

}
