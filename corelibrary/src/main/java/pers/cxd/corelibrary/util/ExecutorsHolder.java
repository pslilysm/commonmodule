package pers.cxd.corelibrary.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import pers.cxd.corelibrary.Singleton;

public class ExecutorsHolder {

    private static final Singleton<ExecutorService> sGlobalCachedExecutor = new Singleton<ExecutorService>() {
        @Override
        protected ExecutorService create() {
            return Executors.newCachedThreadPool();
        }
    };

    private static final Singleton<ScheduledExecutorService> sGlobalScheduledExecutor = new Singleton<ScheduledExecutorService>() {
        @Override
        protected ScheduledExecutorService create() {
            return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        }
    };

    public static ExecutorService getGlobalCachedExecutor(){
        return sGlobalCachedExecutor.getInstance();
    }

    public static ScheduledExecutorService getGlobalScheduledExecutor(){
        return sGlobalScheduledExecutor.getInstance();
    }

}
