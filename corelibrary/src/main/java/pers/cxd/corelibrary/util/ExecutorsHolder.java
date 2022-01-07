package pers.cxd.corelibrary.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import pers.cxd.corelibrary.Singleton;

/**
 * A global executors holder
 *
 * @author pslilysm
 * @since 1.0.0
 */
public class ExecutorsHolder {

    private static final AtomicInteger sIONum = new AtomicInteger();

    private static final Singleton<ExecutorService> sGlobalIOExecutor = new Singleton<ExecutorService>() {
        @Override
        protected ExecutorService create() {
            return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5,
                    r -> new Thread(r, "g-io-" + sIONum.incrementAndGet() + "-thread"));
        }
    };

    private static final AtomicInteger sScheduledNum = new AtomicInteger();

    private static final Singleton<ScheduledExecutorService> sGlobalScheduledExecutor = new Singleton<ScheduledExecutorService>() {
        @Override
        protected ScheduledExecutorService create() {
            return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
                    r -> new Thread(r, "g-scheduled-" + sScheduledNum.incrementAndGet() + "-thread"));
        }
    };

    /**
     * @return a global io executor, the core pool size is {@code cpu cores * 5}
     */
    public static ExecutorService io(){
        return sGlobalIOExecutor.getInstance();
    }

    /**
     * @return a global scheduled executor, the core pool size is cpu cores
     */
    public static ScheduledExecutorService scheduled(){
        return sGlobalScheduledExecutor.getInstance();
    }

}
