package pers.cxd.corelibrary.util;

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

    private static final Singleton<ScheduledExecutorService> sGlobalIOExecutor = new Singleton<ScheduledExecutorService>() {
        @Override
        protected ScheduledExecutorService create() {
            return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 5,
                    r -> new Thread(r, "g-io-" + sIONum.incrementAndGet() + "-thread"));
        }
    };

    private static final AtomicInteger sComputeNum = new AtomicInteger();

    private static final Singleton<ScheduledExecutorService> sGlobalComputeExecutor = new Singleton<ScheduledExecutorService>() {
        @Override
        protected ScheduledExecutorService create() {
            return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(),
                    r -> new Thread(r, "g-computation-" + sComputeNum.incrementAndGet() + "-thread"));
        }
    };

    /**
     * @return a global io executor, the core pool size is {@code cpu cores * 5}
     */
    public static ScheduledExecutorService io(){
        return sGlobalIOExecutor.getInstance();
    }

    /**
     * @return a global compute executor, the core pool size is cpu cores
     */
    public static ScheduledExecutorService compute(){
        return sGlobalComputeExecutor.getInstance();
    }

}
